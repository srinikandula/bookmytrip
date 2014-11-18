package com.bookmytrip;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bookmytrip.dao.UserMongoDAO;
import com.bookmytrip.domain.User;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionManager {

    public static final String USER_SESSION_ATTR = "currentUser";
    public static final String SCREEN_SESSION_ATTR = "currentScreenSession";
    public static final String SESSION_TOKEN_COOKIE = "session_token";


    public static final int DEFAULT_SESSION_EXPIRATION_SECS = 60 * 60 * 3; // 3 hours
    public static final int PORTAL_COOKIE_EXPIRATION_SECS = 60 * 60 * 24 * 90;  // 90 days

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    @Autowired
    private UserMongoDAO userMongoDAO;

    
    @Autowired
    private HttpSessionCacheManager httpSessionCacheManager;

   
    @Autowired
    private SystemProperties props;

    public static HttpServletRequest getRequest() {
        try {
            return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        } catch (Exception e) {
            logger.warn("Unable to get current HTTP Request.", e);
        }
        return null;
    }

    public User getCurrentUser() {
        return getCurrentUser(getRequest());
    }

    public static User getCurrentUser(final HttpServletRequest httpServletRequest) {
        return (User) httpServletRequest.getAttribute(SessionManager.USER_SESSION_ATTR);
    }

    public void setCurrentUser(final User user) {
        if (user == null) {
            getRequest().removeAttribute(SessionManager.USER_SESSION_ATTR);
        } else {
            getRequest().setAttribute(SessionManager.USER_SESSION_ATTR, user);
        }
    }

    public String getCurrentUserId() {
        if (getCurrentUser() != null) {
            return getCurrentUser().getId();
        }
        return null;
    }

    public void setCurrentScreenSession(final ScreenSession screenSession) {
        if (screenSession == null) {
            getRequest().removeAttribute(SessionManager.SCREEN_SESSION_ATTR);
        } else {
            getRequest().setAttribute(SessionManager.SCREEN_SESSION_ATTR, screenSession);
        }
    }

    public static ScreenSession getCurrentScreenSession(final HttpServletRequest httpServletRequest) {
        return (ScreenSession) httpServletRequest.getAttribute(SessionManager.SCREEN_SESSION_ATTR);
    }

    public ScreenSession getCurrentScreenSession() {
        return getCurrentScreenSession(getRequest());
    }

    public boolean isSessionTokenExpired(final String sessionToken) {
        int expirationInSecs = props.getIntegerProperty(SESSION_EXPIRATION_SECONDS, DEFAULT_SESSION_EXPIRATION_SECS);
        return ServiceUtils.isSessionTokenExpired(getCurrentUser(), sessionToken, DateTime.now(), expirationInSecs);
    }

    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiObject(name = "LogoutResponseBody")
    public static class LogoutResponseBody {
        @Getter
        @Setter
        @ApiObjectField(description = "States Whether Successful")
        private boolean success;
    }

    /**
     * helper method to log out the current user.  this action results in the 'disconnect' command
     * being sent to any connected receiving screens
     * @return boolean indicating success or failure
     */
    public boolean logout(final HttpServletResponse httpServletResponse, final boolean deactivateScreensAndEvents) {
        logger.trace("SessionManager - logout called");

        httpSessionCacheManager.deleteSessionDataInRedisForSessionToken(getSessionToken());

        expireSessionTokenCookie(httpServletResponse);

        User user = getCurrentUser();
        if (user != null) {
            setCurrentUser(null);
            userMongoDAO.removeSessionTokens(user.getId(), new String[]{getSessionToken()});
            userMongoDAO.setFirstTimeUserField(user.getId(), false);
            if (deactivateScreensAndEvents) {
                // inactivate any event urls that were left open (plus their events & screen sessions)
                eventUrlManager.deactivateAllEventUrlsForUser(user, this);
            }
            if (!StringUtils.isEmpty(user.getApiKey())) {
                httpSessionCacheManager.deleteSessionDataInRedisForAPIKey(user.getApiKey());
            }
        }
        ScreenSession screenSession = getCurrentScreenSession();

        if (deactivateScreensAndEvents) {
            screenManager.disconnectScreenSession(screenSession, this);
            setCurrentScreenSession(null);
        }
        if (user != null && deactivateScreensAndEvents) {
            inactivateAndDisconnectAllScreenSessionsForUser(user.getId());
        }
        pruneExpiredSessionsFromUserObject(user);
        // Should we set sessionManager current user to null here?
        return true;
    }

    public void pruneExpiredSessionsFromUserObject(final User user) {
        if (user == null) {
            logger.debug("null user passed to pruneExpiredSessionsFromUserObject()");
            return;
        }
        if (logger.isTraceEnabled()) {
            logger.trace("pruneExpiredSessionsFromUserObject() called for user " + user.getEmail());
        }
        final Set<String> sessionTokensToDelete = new HashSet<>();
        for (String token : user.getSessionTokens()) {
            if (!httpSessionCacheManager.isSessionTokenCached(token)) {
                sessionTokensToDelete.add(token);
            }
        }
        userMongoDAO.removeSessionTokens(user.getId(),
                sessionTokensToDelete.toArray(new String[sessionTokensToDelete.size()]));
    }

    /**
     * This will send a disconnect command to every active session that a user owns.
     * It also inactivates that session as well as all of the screens in that
     * session, and saves the changes to the db.
     * @param userId the user's id
     */
    public void inactivateAndDisconnectAllScreenSessionsForUser(final String userId) {
        inactivateAndDisconnectAllScreenSessionsForUser(userId, null);
    }

    /**
     * This will send a disconnect command to every active session that a user owns.
     * It also inactivates that session as well as all of the screens in that
     * session, and saves the changes to the db.  It ignores any screen sessions that are specified
     * in the excludedScreenSessionIds list.  This also cascades into event urls and deactivates them
     * if they are associated with the screen session being disconnected.
     * @param userId the user's id
     * @param excludedScreenSessionIds screen session ids that will NOT be inactivated/disconnected
     */
    public void inactivateAndDisconnectAllScreenSessionsForUser(final String userId,
                                                                final List<String> excludedScreenSessionIds) {
        if (logger.isDebugEnabled()) {
            logger.debug("inactivateAndDisconnectAllScreenSessionsForUser() called for userId " + userId
                    + " with exceptions for these screenSessionIds: " + (excludedScreenSessionIds == null
                    ? "null" : Arrays.toString(excludedScreenSessionIds.toArray())));
        }

        List<ScreenSession> sessions = screenSessionMongoDAO.findActiveSessionByUserId(userId);
        if (logger.isDebugEnabled()) {
            logger.debug(format("%d active screen sessions were found for user '%s'%s",
                    sessions.size(), userId, (sessions.isEmpty() ? "." : " and they will be inactivated now.")));
        }
        if (sessions != null && !sessions.isEmpty()) {
            for (ScreenSession session : sessions) {
                if (excludedScreenSessionIds == null || !excludedScreenSessionIds.contains(session.getId())) {
                    screenManager.disconnectScreenSessionCascadingEffects(session, this);
                }
            }
        }
    }

    /**
     * sets the session token cookie to be expired
     */
    public static void expireSessionTokenCookie(final HttpServletResponse httpServletResponse) {
        if (httpServletResponse == null) {
            return;
        }

        Cookie cookie = getSessionTokenCookie();
        if (cookie == null) {
            cookie = new Cookie(SESSION_TOKEN_COOKIE, "");
        }
        cookie.setValue("");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setComment("EXPIRING COOKIE at " + System.currentTimeMillis());
        httpServletResponse.addCookie(cookie);
    }

    /**
     * gets the String value of the current user's session token
     * @return the current session token (from a cookie) or null if none exists
     */
    public String getSessionToken() {
        return getSessionToken(getRequest());
    }

    public static String getSessionToken(final HttpServletRequest request) {
        Cookie sessionTokenCookie = getSessionTokenCookie(request);
        if (sessionTokenCookie != null) {
            return sessionTokenCookie.getValue();
        }
        return null;
    }

    public String createSessionTokenCookie(final HttpServletResponse response) {
        String generatedToken = httpSessionCacheManager.generateUniqueKey();
        getRequest().setAttribute(REQUEST_ATTR_NEW_SESSION_TOKEN_COOKIE, generatedToken);
        if (logger.isTraceEnabled()) {
            logger.trace(format("createSessionTokenCookie() called -- new session token generated with value of '%s'",
                    generatedToken));
        }
        Cookie sessionTokenCookie = new Cookie(SESSION_TOKEN_COOKIE, generatedToken);
        int maxAge = props.getIntegerProperty(SESSION_EXPIRATION_SECONDS, DEFAULT_SESSION_EXPIRATION_SECS);
        sessionTokenCookie.setMaxAge(maxAge);
        sessionTokenCookie.setSecure(false);
        sessionTokenCookie.setPath("/");
        response.addCookie(sessionTokenCookie);
        return sessionTokenCookie.getValue();
    }

    public String createPortalCookie(final HttpServletResponse response) {
        Cookie sessionTokenCookie = new Cookie(PORTAL_COOKIE, String.valueOf(true));
        sessionTokenCookie.setMaxAge(PORTAL_COOKIE_EXPIRATION_SECS);
        sessionTokenCookie.setSecure(false);
        sessionTokenCookie.setPath("/");
        response.addCookie(sessionTokenCookie);
        return sessionTokenCookie.getValue();
    }

    public static Cookie getPortalCookie(final HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Cookie cookie = ServiceUtils.getCookieByName(PORTAL_COOKIE, request.getCookies());
        if (logger.isTraceEnabled()) {
            logger.trace("the portal cookie value is: " + cookie);
        }
        return cookie;
    }

    public static Cookie getSessionTokenCookie() {
        return getSessionTokenCookie(getRequest());
    }

    public static Cookie getSessionTokenCookie(final HttpServletRequest request) {
        if (request == null) {
            logger.warn("null HttpServletRequest encountered in getSessionTokenCookie()");
            return null;
        }
        return ServiceUtils.getCookieByName(SESSION_TOKEN_COOKIE, request.getCookies());
    }

    private static final int RADIX = 16;
    private static final int RANDOM_STR_BYTE_SIZE = 24;

    public static String getRandomString(final int length, final String validCharacters) {
        BigInteger validCharCount = new BigInteger(String.valueOf(validCharacters.length()));
        BigInteger maxValue = validCharCount.pow(length);
        SecureRandom secureRandom = new SecureRandom(new ObjectId().toByteArray());
        byte[] saltBytes = new byte[RANDOM_STR_BYTE_SIZE];
        secureRandom.nextBytes(saltBytes);
        String baseId = sha1().hashString(new ObjectId().toString()).toString();
        BigInteger screenId = new BigInteger(baseId, RADIX);
        BigInteger randomNumerical = screenId.mod(maxValue);
        logger.trace("randomNumerical is " + randomNumerical.toString());
        String randomStr = "";
        BigInteger radix = new BigInteger(String.valueOf(validCharacters.length()));
        for (int i = 0; i < length; i++) {
            int digit = randomNumerical.mod(radix).intValue();
            randomStr = validCharacters.charAt(digit) + randomStr;
            randomNumerical = randomNumerical.divide(radix);
        }
        return randomStr;
    }

}
