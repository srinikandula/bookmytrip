package com.bookmytrip;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

import static java.lang.String.format;

@ManagedResource
@ContextConfiguration(classes = {CoreAppConfig.class})
public final class SystemProperties {

    @Autowired
    private Environment env;

    private Properties homeDirProperties;

    private Properties manuallySetProperties;

    private static final Logger logger = LoggerFactory.getLogger(SystemProperties.class);

    private static final String HOME_DIR_PROPS_FILENAME = ".ube.properties";

    public static enum SysProps {
        ANALYTICS_FLURRY_CUSTOMER_SITES("analytics.flurry.customer.sites"),
        API_KEY_LOGIN_ENABLED("api.key.login.enabled"),
        AWS_SQS_JOB_RUNNER_COMMAND_QUEUE("aws.sqs.jobrunner.command.queue"),
        AWS_SQS_JOB_RUNNER_CALLBACK_QUEUE("aws.sqs.jobrunner.callback.queue"),
        AWS_SQS_LONG_POLLING_WAIT_TIME_SECS("aws.sqs.long.poll.wait.time.secs"),
        BILLING_ENABLED("billing.enabled"),
        BOX_API_KEY("box.api.key"),
        BOX_MAX_RETRIES("box.max.retries"),
        BUILD_NUMBER("build.number"),
        CACHE_TTL_SECONDS_REDIS_API_KEY_SESSION_INFO("cache.ttl.seconds.redis.api.key.session.info"),
        CHARGIFY_API_KEY("chargify.api.key"),
        CHARGIFY_SITE_SHARED_KEY("chargify.site.shared.key"),
        CHARGIFY_SUBDOMAIN("chargify.subdomain"),
        CHARGIFY_DEFAULT_PLAN_PRODUCT_ID("chargify.default.plan.product.id"),
        CONVERSION_SERVICE_FOR_MS_WORD("conversion.service.for.ms.word"),
        CONVERSION_SERVICE_FOR_PDF("conversion.service.for.pdf"),
        CONVERSION_SERVICE_FOR_POWERPOINT("conversion.service.for.powerpoint"),
        CLOUDFRONT_BASE_URL("cloudfront.base.url"),
        CLOUDFRONT_PROTOCOL("cloudfront.protocol"),
        CLOUDFRONT_BASE_URL_FOR_BOX_ASSETS("cloudfront.base.url.for.box.assets"),
        CLOUDFRONT_PROTOCOL_FOR_BOX_ASSETS("cloudfront.protocol.for.box.assets"),
        COMPANY_SETTING_ADMINS_CREATE_ADMINS("company.setting.admins.create.admins"),
        COMPANY_SETTING_ADMINS_CREATE_CHILD_COMPANY("company.setting.admins.create.child.company"),
        COMPANY_SETTING_ADMINS_CREATE_CHILD_COMPANY_ADMINS("company.setting.admins.create.child.company.admins"),
        COMPANY_SETTING_ADMINS_CREATE_CHILD_COMPANY_USERS("company.setting.admins.create.child.company.users"),
        COMPANY_SETTING_ADMINS_DELETE_CHILD_COMPANY("company.setting.admins.delete.child.company"),
        COMPANY_SETTING_ADMINS_DELETE_OWN_COMPANY("company.setting.admins.delete.own.company"),
        COMPANY_SETTING_CAN_BE_VIEWED_BY_CHILDREN_ADMINS("company.setting.can.be.viewed.by.children.admins"),
        COMPANY_SETTING_CHILD_COMPANIES_CAN_ACCESS_RESOURCES("company.setting.child.companies.can.access.resources"),
        COMPANY_SETTING_DISABLE_BILLING("company.setting.disable.billing"),
        COMPANY_SETTING_PARENT_COMPANY_CAN_ACCESS_RESOURCES("company.setting.parent.company.can.access.resources"),
        COMPANY_SETTING_EMAIL_ASSET_LINKS_SUBJECT("company.setting.email.asset.links.subject"),
        COMPANY_SETTING_EMAIL_EVENT_INVITATION_SUBJECT("company.setting.email.event.invitation.subject"),
        COMPANY_SETTING_EMAIL_FROM_ADDRESS("company.setting.email.from.address"),
        COMPANY_SETTING_EMAIL_FROM_NAME("company.setting.email.from.name"),
        COMPANY_SETTING_EMAIL_PASSWORD_RESET_SUBJECT("company.setting.email.password.reset.subject"),
        COMPANY_SETTING_EMAIL_TEMPLATE_EVENT_URL_OWNER_ASSETS("company.setting.email.template.event.url.owner.assets"),
        COMPANY_SETTING_EMAIL_TEMPLATE_QUICK_CONNECT_OWNER_ASSETS(
                "company.setting.email.template.quick.connect.owner.assets"),
        COMPANY_SETTING_EMAIL_TEMPLATE_PASSWORD_RESET("company.setting.email.template.password.reset"),
        COMPANY_SETTING_PORTAL_ENABLE_HPSN("company.setting.portal.enable.hpsn"),
        COMPANY_SETTING_USERS_CAN_MODIFY_COMPANY_FOLDERS("company.setting.users.can.modify.company.folders"),
        COMPANY_DEFAULT_GROUP_CREATION_ENABLED("company.default.group.creation.enabled"),
        CROCODOC_API_KEY("crocodoc.api.key"),
        CROCODOC_CONVERSION_ENABLED("crocodoc.conversion.enabled"),
        CROCODOC_MAX_RETRIES("crocodoc.max.retries"),
        CROSSDOMAIN_HOST_WHITELIST_CSV("crossdomain.host.whitelist.csv"),
        DASHBOARD_ENABLED("dashboard.enabled"),
        DEFAULT_GROUPS_FOR_IMPORTED_ASSETS_ENABLED("default.groups.for.imported.assets.enabled"),
        DEFAULT_CONTAINER_FOR_IMPORTED_ASSETS_ENABLED("default.container.for.imported.assets.enabled"),
        DEFAULT_CONTAINER_FOR_IMPORTED_ASSETS_IS_FOLDER("default.container.for.imported.assets.is.folder"),
        DEVELOPER_PORTAL_ENABLED("developer.portal.enabled"),
        DEVELOPER_PORTAL_SCREENDIRECT_NAME("developer.portal.screendirect.name"),
        DEVELOPER_SIGNUP_ENABLED("developer.signup.enabled"),
        DROPBOX_APP_KEY("dropbox.app.key"),
        DROPBOX_APP_SECRET("dropbox.app.secret"),
        EMAIL_ENABLED("email.enabled"),
        EMAIL_LINK_SIGNUP_VERIFICATION_EXPIRATION_DAYS("email.link.signup.verification.expiration.days"),
        EMAIL_LINK_COMPANY_INVITE_EXPIRATION_DAYS("email.link.company.invite.expiration.days"),
        EMAIL_LINK_SLIDE_SHARE_EXPIRATION_DAYS("email.link.slide.share.expiration.days"),
        EMAIL_SENDER_CONCURRENT_THREAD_LIMIT("email.sender.concurrent.thread.limit"),
        EMAIL_SENDER_HOST("email.sender.host"),
        EMAIL_SENDER_PASSWORD("email.sender.password"),
        EMAIL_SENDER_PORT("email.sender.port"),
        EMAIL_SENDER_PROTOCOL("email.sender.protocol"),
        EMAIL_SENDER_USERNAME("email.sender.username"),
        EMAIL_SIGNATURE_FROM("email.signature.from"),
        EMBEDLY_API_KEY("embedly.api.key"),
        EVENTURL_AUTOCREATE_ENABLED("eventurl.autocreate.enabled"),
        EVENTURL_DISCONNECT_EMAIL_ASSETS_TO_OWNER("eventurl.disconnect.email.assets.to.owner"),
        EVENTURL_MAX_ALLOWED_PER_USER("eventurl.max.allowed.per.user"),
        EVENTURL_NAME("eventurl.name"),
        EVENTURL_RANDOM_URI_LENGTH("eventurl.random.uri.length"),
        EVENTURL_URI_PATH_PREFIX("eventurl.uri.path.prefix"),
        FACEBOOK_TESTING_ACCESS_TOKEN("facebook.testing.access.token"),
        FILE_STORAGE_PROVIDER("file.storage.provider"),
        FLICKR_CONSUMER_KEY("flickr.consumer.key"),
        FLICKR_CONSUMER_SECRET("flickr.consumer.secret"),
        FLICKR_TESTING_ACCESS_TOKEN("flickr.testing.access.token"),
        FLICKR_TESTING_ACCESS_TOKEN_SECRET("flickr.testing.access.token.secret"),
        FOLDER_NEW_ASSET_COUNT_ENABLED("folder.new.asset.count.enabled"),
        GENERATE_THUMBNAILS_FOR_IMAGE_URL_ASSETS("generate.thumbnails.for.image.url.assets"),
        GENERATE_THUMBNAILS_FOR_VIDEO_URL_ASSETS("generate.thumbnails.for.video.url.assets"),
        GOOGLE_ANALYTICS_RECEIVING_SCREEN_ID("google.analytics.receiving.screen.id"),
        GOOGLE_ANALYTICS_PORTAL_ID("google.analytics.portal.id"),
        GOOGLE_APP_NAME("google.app.name"),
        GOOGLE_TESTING_ACCESS_TOKEN("google.testing.access.token"),
        HPCLOUD_CREDENTIAL("hpcloud.credential"),
        HPCLOUD_IDENTITY("hpcloud.identity"),
        HPCLOUD_OBJECTSTORE_AZ("hpcloud.objectstore.availability.zone"),
        HPCLOUD_OBJECTSTORE_BUCKET("hpcloud.objectstore.bucket"),
        HPCLOUD_PROJECT("hpcloud.project"),
        HPCLOUD_PROJECT_ID("hpcloud.project.id"),
        HP_SAML_ASSERTION_CONSUMER_PATH("hp.saml.assertion.consumer.path"),
        HP_SAML2_ASSERTION_CONSUMER_PATH("hp.saml2.assertion.consumer.path"),
        HP_SAML_IDP_ENDPOINT("hp.saml.idp.url"),
        HP_SAML2_IDP_ENDPOINT("hp.saml2.idp.url"),
        HP_SAML_ISSUER("hp.saml.issuer"),
        HP_SAML2_ISSUER("hp.saml2.issuer"),
        HP_SAML_PKCS12_FILE("hp.saml.pkcs12.file"),
        HP_SAML2_PKCS12_FILE("hp.saml2.pkcs12.file"),
        HP_SAML_X509_CERTIFICATE("hp.saml.x509.certificate"),
        HP_SAML2_X509_CERTIFICATE("hp.saml2.x509.certificate"),
        HP_SECURITY_GATEWAY("hp.security.gateway.gatewayurl"),
        HP_SECURITY_GATEWAY_SERVICEURN("urn:hp:hpit:hpmedia:salesnow"),
        HP_SECURITY_GATEWAY_TRUSTSTOREFILE("hp.security.gateway.truststorefile"),
        HP_SECURITY_GATEWAY_TRUSTSTOREPASS("hp.security.gateway.truststorepass"),
        HPNN_HMAC_SECRET_KEY("hpnn.hmac.secret.key"),
        HPNN_HMAC_MAX_TIMESTAMP_DELTA_SECONDS("hpnn.hmac.max.timestamp.delta.seconds"),
        HPP_AUTHORIZED_CALLBACK_URL("hpp.authorized.callback.url"),
        HPP_CF_LOGIN_FORM_URL("hpp.cf.login.form.url"),
        HPP_FINAL_REDIRECT_URL("hpp.final.redirect.url"),
        HPP_WS_USERNAME("hpp.ws.username"),
        HPP_WS_PASSWORD("hpp.ws.password"),
        HPP_WS_WSDL_URL("hpp.ws.wsdl.url"),
        HP_SECURITY_GATEWAY_ENABLED("hp.security.gateway.enabled"),
        HP_SECURITY_GATEWAY_AUTH_REQUIRED("hp.security.gateway.auth.required"),
        JOB_RUNNER_QUEUE_METHOD("job.runner.queue.method"),
        JOB_RUNNER_OUTGOING_REDIS_QUEUE_NAME("job.runner.outgoing.redis.queue.name"),
        JOB_RUNNER_INCOMING_REDIS_QUEUE_NAME("job.runner.incoming.redis.queue.name"),
        JSON_PREFIX_TAINT_ENABLED("json.prefix.taint.enabled"),
        MAX_UPLOAD_SIZE_BYTES("max.upload.size.bytes"),
        MOBILE_WEB_SIMULATOR_ENABLED("mobile.web.simulator.enabled"),
        MULTIPLE_LOGINS_ENABLED("multiple.logins.enabled"),
        NEW_USER_CAN_ACCESS_PORTAL("new.user.can.access.portal"),
        NEW_USER_CAN_UPLOAD_FROM_PORTAL("new.user.can.upload.from.portal"),
        NEW_USER_CAN_APPROVE_UPLOADS("new.user.can.approve.uploads"),
        PASSWORD_RESET_TOKEN_EXPIRATION_HOURS("password.reset.token.expiration.hours"),
        PASSWORD_RESET_TOKEN_LENGTH("password.reset.token.length"),
        PORTAL_ANALYTICS_VIEWS_ENABLED("portal.analytics.views.enabled"),
        PORTAL_REQUIRES_SAML("portal.requires.saml"),
        PRODUCT_NAME("product.name"),
        QUICK_CONNECT_DISCONNECT_EMAIL_ASSETS_TO_OWNER("quick.connect.disconnect.email.assets.to.owner"),
        RECEIVING_SCREEN_DEBUG_MODE_ENABLED("receiving.screen.debug.mode.enabled"),
        RECEIVING_SCREEN_URL("receiving.screen.url"),
        RECEIVING_SCREEN_SESSION_STATUS_CHECK_ENABLED("receiving.screen.session.status.check.enabled"),
        RECEIVING_SCREEN_INACTIVE_DISCONNECT_TIME_SECONDS("receiving.screen.inactive.disconnect.time.seconds"),
        RECEIVING_SCREEN_VIEW_NAME("receiving.screen.view.name"),
        REDIS_CHANNEL_SUBSCRIPTION_NODEJS("redis.channel.subscription.nodejs"),
        REPO_FOLDER_AUTO_CREATE_DROPBOX("repo.folder.auto.create.dropbox"),
        REPO_FOLDER_AUTO_CREATE_UPLOADS("repo.folder.auto.create.uploads"),
        REPO_FOLDER_AUTO_CREATE_YOUTUBE("repo.folder.auto.create.youtube"),
        REST_API_DOCS_ENABLED("rest.api.docs.enabled"),
        ROOT_INDEX_VIEW_NAME("root.index.view.name"),
        ROUTE_53_HOSTED_ZONE_ID("route53.hosted.zone.id"),
        S3_BUCKET("s3.bucket"),
        S3_BUCKET_EXTRACTED_ASSETS("s3.bucket.extracted.assets"),
        SCREEN_SESSION_AUTO_END_TIME_AFTER_N_MINUTES("screen.session.auto.end.time.after.n.minutes"),
        SCREEN_SESSION_EXPIRATION_MINUTES("screen.session.expiration.minutes"),
        SCREEN_SESSION_STARTED_AT_BEGINS_UPON_SYNC("screen.session.started.at.begins.upon.sync"),
        SERVICE_DROPBOX_ENABLED("service.dropbox.enabled"),
        SERVICE_FACEBOOK_ENABLED("service.facebook.enabled"),
        SERVICE_FLICKR_ENABLED("service.flickr.enabled"),
        SERVICE_GOOGLEPLUS_ENABLED("service.googleplus.enabled"),
        SERVICE_TWITTER_ENABLED("service.twitter.enabled"),
        SELENIUM_USER_NAME("selenium.user.name"),
        SELENIUM_USER_PWD("selenium.user.pwd"),
        SELENIUM_URL_DEFAUL("selenium.url.default"),
        SELENIUM_URL_ENV_VAR_NAME("selenium.url.env.var.name"),
        SESSION_EXPIRATION_SECONDS("session.expiration.seconds"),
        SUBDOMAIN_MODIFICATION_ENABLED("subdomain.modification.enabled"),
        SURVEY_PAGE_INDEX_CONTAINING_QUESTIONS("survey.page.index.containing.questions"),
        SURVEYGIZMO_USERNAME("surveygizmo.username"),
        SURVEYGIZMO_PW_MD5("surveygizmo.pw.md5"),
        SYNC_CODE_EXPIRATION_MINUTES("sync.code.expiration.minutes"),
        SYNC_CODE_CHARS("sync.code.characters"),
        SYNC_CODE_LENGTH("sync.code.length"),
        THUMB_HQ_COLOR_DEPTH("thumbnail.hq.color.depth"),
        THUMB_HQ_EXTENSION("thumbnail.hq.extension"),
        THUMB_HQ_JPEG_QUALITY("thumbnail.hq.jpeg.quality"),
        THUMB_HQ_PNG_DENSITY("thumbnail.hq.png.density"),
        THUMB_HQ_SCALE("thumbnail.hq.scale"),
        THUMB_LARGE_HEIGHT("thumbnail.large.height"),
        THUMB_LARGE_WIDTH("thumbnail.large.width"),
        THUMB_SMALL_HEIGHT("thumbnail.small.height"),
        THUMB_SMALL_WIDTH("thumbnail.small.width"),
        THUMBNAIL_DEFAULT_PDF("thumbnail.default.pdf"),
        THUMBNAIL_DEFAULT_PPT("thumbnail.default.ppt"),
        THUMBNAIL_DEFAULT_VIDEO("thumbnail.default.video"),
        THUMBNAIL_DEFAULT_YOUTUBE("thumbnail.default.youtube"),
        TWILIO_ACCOUNT_CALLER_ID("twilio.account.caller.id"),
        TWILIO_ACCOUNT_AUTH_TOKEN("twilio.account.auth.token"),
        TWILIO_ACCOUNT_SID("twilio.account.sid"),
        TWITTER_CONSUMER_KEY("twitter.consumer.key"),
        TWITTER_CONSUMER_SECRET("twitter.consumer.secret"),
        TWITTER_TESTING_ACCESS_TOKEN("twitter.testing.access.token"),
        TWITTER_TESTING_ACCESS_TOKEN_SECRET("twitter.testing.access.token.secret"),
        UDID_LOGIN_ENABLED("udid.login.enabled"),
        UPLOAD_CONTENT_TYPE_MP4_ENABLED("upload.content.type.mp4.enabled"),
        USER_PORTAL_ENABLED("user.portal.enabled"),
        USER_PORTAL_URL("user.portal.url"),
        WEBSOCKET_HOST("websocket.host"),
        WEBSOCKET_PORT("websocket.port"),
        WEBSOCKET_SSL_ENABLED("websocket.ssl.enabled");

        private final String propertyName;

        SysProps(final String propertyName) {
            this.propertyName = propertyName;
        }

        public String getPropertyName() {
            return this.propertyName;
        }
    }

    public boolean hasNonBlankProperty(final SysProps property) {
        return !StrUtils.isBlank(getProperty(property));
    }

    @ManagedOperation(description = "Set a system property")
    public void setProperty(final String propertyName, final String value) {
        manuallySetProperties.setProperty(propertyName, value);
    }

    public void setProperty(final SysProps property, final String value) {
        setProperty(property.getPropertyName(), value);
    }

    @ManagedOperation(description = "Get a list of all current properties, in JSON format")
    public String getCurrentPropertyValues() {
        return getCurrentPropertyValuesJSON().toJSONString();
    }

    @SuppressWarnings("unchecked")
    public JSONObject getCurrentPropertyValuesJSON() {
        Set<String> keys = new HashSet<>(SysProps.values().length * 2);
        for (SysProps sysProps : SysProps.values()) {
            keys.add(sysProps.getPropertyName());
        }
        if (manuallySetProperties != null) {
            for (Object key : manuallySetProperties.keySet()) {
                keys.add(key.toString());
            }
        }
        if (homeDirProperties != null) {
            for (Object o : homeDirProperties.keySet()) {
                keys.add(o.toString());
            }
        }
        // there aren't any public getters for the values stored in 'env'.  Might need to use reflection.

        Map m = new TreeMap();
        for (String key : keys) {
            m.put(key, this.getProperty(key));
        }
        return new JSONObject(m);
    }

    private SystemProperties() {
        if (logger.isTraceEnabled()) {
            logger.trace("Initializing SystemProperties singleton instance");
        }
        this.manuallySetProperties = new Properties();
    }

    public SystemProperties(final Environment environment) {
        if (environment == null) {
            throw new IllegalArgumentException("Environment cannot be null when initializing SystemProperties");
        }
        this.env = environment;
        this.homeDirProperties = getPropertiesFromHomeDir();
        this.manuallySetProperties = new Properties();
    }

    public String getProperty(final String propertyName) {
        if (manuallySetProperties != null && manuallySetProperties.containsKey(propertyName)) {
                return manuallySetProperties.getProperty(propertyName);
        }
        if (homeDirProperties != null && homeDirProperties.containsKey(propertyName)) {
                return homeDirProperties.getProperty(propertyName);
        }
        return env.getProperty(propertyName, "");
    }

    public String getProperty(final SysProps property) {
        return getProperty(property.getPropertyName());
    }

    public int getIntegerProperty(final String propertyName) {
        return Integer.valueOf(getProperty(propertyName));
    }

    public int getIntegerProperty(final String propertyName, final int defaultValue) {
        try {
            return Integer.valueOf(getProperty(propertyName));
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public int getIntegerProperty(final SysProps property) {
        return getIntegerProperty(property.getPropertyName());
    }

    public int getIntegerProperty(final SysProps property, final int defaultValue) {
        return getIntegerProperty(property.getPropertyName(), defaultValue);
    }

    public boolean getBooleanProperty(final SysProps property, final boolean defaultValue) {
        String propertyValue = getProperty(property.getPropertyName());
        return getBooleanValue(propertyValue, defaultValue);
    }

    public boolean getBooleanProperty(final SysProps property) {
        return getBooleanProperty(property, false);
    }

    private boolean getBooleanValue(final String propertyValue, final boolean defaultValue) {
        try {
            return Boolean.valueOf(propertyValue);
        } catch (Exception ex) {
            if (logger.isTraceEnabled()) {
                logger.trace(format("Boolean system property could not be parsed.  Its value was '%s'", propertyValue));
            }
            return defaultValue;
        }
    }

    public boolean getBooleanProperty(final String propertyName, final boolean defaultValue) {
        return getBooleanValue(propertyName, defaultValue);
    }

    public boolean getBooleanProperty(final String propertyName) {
        return getBooleanValue(propertyName, false);
    }

    public String getEnvironmentProperty(final SysProps property, final String def) {
        String propKey = property.getPropertyName();
        return coalesce(System.getProperty(propKey), System.getenv(propKey), getProperty(propKey), def);
    }

    public void reloadPropertiesFromHomeDir() {
        this.homeDirProperties = getPropertiesFromHomeDir();
    }

    private static String coalesce(String... strings) {
        for (String string : strings) {
            if (!StringUtils.isEmpty(string)) {
                return string;
            }
        }
        return null;
    }

    private static Properties getPropertiesFromHomeDir() {
        return PropertiesUtils.getPropertiesFromHomeDirFile(HOME_DIR_PROPS_FILENAME);
    }
}
