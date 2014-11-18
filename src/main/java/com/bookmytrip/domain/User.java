package com.bookmytrip.domain;


import static java.lang.String.format;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

@ToString
@EqualsAndHashCode(callSuper = false, of = { "email" })
@ApiObject(name = "User")
public class User extends AbstractDocument {

    public static final String KEY_ACTIVE = "active";
    public static final String KEY_SELECTED_GROUP_IDS = "g";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_COMPANY_IDS = "companyIds";
    public static final String KEY_FIRST_TIME_USER = "firstTimeUser";
    public static final String KEY_FOLDERS = "folders";
    public static final String KEY_GROUPS = "groups";
    public static final String KEY_LAST_LOGIN_TIME = "lastLoginTime";
    public static final String KEY_SESSION_TOKENS = "sessionTokens";
    public static final String KEY_VERIFICATION_LINK_ID = "v";
    public static final String KEY_API_KEY = "apiKey";
    public static final String KEY_PHONE1 = "p1";
    public static final String KEY_COMPANY_NAME = "c";
    public static final String KEY_SALT = "salt";
    public static final String KEY_ADMIN = "admin";
    public static final String KEY_LOGIN_STATUS = "loginStatus";
    public static final String KEY_REPO_METADATA = "repoMetadata";
    public static final String KEY_ADDRESSES = "addresses";
    public static final String KEY_CLIENT_INFORMATION = "clientInformation";
    public static final String KEY_API_KEY_METADATA = "am";
    public static final String KEY_API_KEY_PENDING_APPROVAL = "pa";
    public static final String KEY_PERMISSIONS = "prm";
    public static final String KEY_FAVORITES = "fav";
    public static final String KEY_AGGREGATE_PERMISSIONS = "agp";
    public static final String KEY_UDID = "udid";

    public static final String KEY_ATTR_ACTIVE_REC_SCREEN_STYLE_ID = "activeRSStyle";

    private static final int RANDOM_PASSWORD_LENGTH = 64;
    private static final int MAX_SESSION_TOKENS = 10;

    private User() {
    }

    public User(final String firstName, final String lastName, final String email, final String clientHashedPassword) {
        validateEmail(email);
        validatePassword(clientHashedPassword);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = clientHashedPassword;
        this.firstTimeUser = true;
    }

    public User(final String firstName, final String lastName, final String email) {
        this(firstName, lastName, email, RandomStringUtils.randomAlphanumeric(RANDOM_PASSWORD_LENGTH));
    }

    @Getter
    @Setter
    @ApiObjectField(description = "First Name")
    private String firstName;

    @Getter
    @Setter
    @ApiObjectField(description = "Last Name")
    private String lastName;

    @Getter
    @Setter
    @ApiObjectField(description = "Title")
    private String title;

    @Getter
    @Setter
    @ApiObjectField(description = "List of GroupIds")
    @Field(KEY_SELECTED_GROUP_IDS)
    private List<String> displaySequenceOfGroupIds = new ArrayList<>();

    @Getter
    // custom setter below
    @NonNull
    @Field(KEY_EMAIL)
    @ApiObjectField(description = "Email Address")
    @Indexed(unique = true)
    private String email;

    @Getter
    // custom setter below
    @NonNull
    @ApiObjectField(description = "Password")
    private String password;

    @Getter
    @Field(KEY_SESSION_TOKENS)
    @ApiObjectField(description = "Session Tokens")
    private Set<String> sessionTokens = new HashSet<>(MAX_SESSION_TOKENS);

    @Getter
    @Setter
    @Field(KEY_SALT)
    @ApiObjectField(description = "Salt")
    private String salt;

    @Getter
    @Setter
    @Field(KEY_ACTIVE)
    @ApiObjectField(description = "States whether the user has an active account")
    private boolean active = true;

    @Getter
    @Setter
    @Field(KEY_ADMIN)
    @ApiObjectField(description = "States Whether User Is Administrator")
    private boolean admin;

    @Getter
    @Setter
    @Field(KEY_LAST_LOGIN_TIME)
    @ApiObjectField(description = "Last time the user logged in")
    private Date lastLoginTime;

    @Getter
    @Setter
    @Field(KEY_LOGIN_STATUS)
    @ApiObjectField(description = "Login Status")
    private int loginStatus;

    @Getter
    @Setter
    @Field(KEY_FIRST_TIME_USER)
    @ApiObjectField(description = "True if the user has never logged in before")
    private boolean firstTimeUser;

    @Getter
    @Setter
    @Field(KEY_VERIFICATION_LINK_ID)
    @ApiObjectField(description = "Verification link id")
    private String verificationLinkId;

    @Getter
    @Field(KEY_GROUPS)
    @DBRef
    @ApiObjectField(description = "List of Groups to which the User Belongs")
    private Set<Group> groups = new LinkedHashSet<>();

   
    @Getter
    @Setter
    @Field(KEY_ADDRESSES)
    @ApiObjectField(description = "List of Addresses for User")
    private Set<Address> addresses = new HashSet<>();

   

    @Getter
    @Setter
    @Field(KEY_COMPANY_IDS)
    @ApiObjectField(description = "List of Companies the user is associated with")
    private Set<String> companyIds = new HashSet<>();

    @Getter
    @Setter
    @Indexed  // not unique because most will be null values
    @Field(KEY_API_KEY)
    private String apiKey;

    @Getter
    @Setter
    @Field(KEY_COMPANY_NAME)
    private String companyName;

    @Getter
    @Setter
    @Field(KEY_PHONE1)
    private String phone1;

    @Getter
    @Setter
    @Field(KEY_API_KEY_PENDING_APPROVAL)
    private boolean apiKeyPendingApproval;

  
    @Getter
    @Setter
    @Field(KEY_PERMISSIONS)
    private Set<UserPermission> permissions = new HashSet<>();

    @Getter
    @Setter
    @Indexed
    @Field(KEY_UDID)
    private String udid;

    @Getter
    @Setter
    @Transient
    @JsonIgnore
    @Field(KEY_AGGREGATE_PERMISSIONS)
    private Set<UserPermission> aggregatePermissions = null;

    @Transient
    @JsonIgnore
    public boolean isUDIDUser() {
       // return !StringU .isBlank(getUdid());
    	return false;
    }

    public void setSessionToken(final String sessionToken) {
        if (sessionTokens == null) {
            sessionTokens = new HashSet<>(MAX_SESSION_TOKENS);
        }
        sessionTokens.add(sessionToken);
    }

    public static void validateEmail(String email) {
    	Strin
        Assert.isTrue(EmailAddressUtils.isValid(email), format("Email address %s must be valid.", email));
    }

    public static void validatePassword(String password) {
        Assert.notNull(password, "User password must not be null");
        Assert.hasText(password, "User password must not be empty");
    }

    public List<UserLinkedRepoMetadata> getRepoInfoByProvider(final RepositoryProvider repositoryProvider) {
        final List<UserLinkedRepoMetadata> repos = new ArrayList<>();
        if (getRepoMetadata() != null) {
            for (UserLinkedRepoMetadata data : getRepoMetadata()) {
                if (repositoryProvider == data.getRepoProvider()) {
                    repos.add(data);
                }
            }
        }
        return repos;
    }

    public Favorite getFavoriteByItemId(String itemId) {
        for (Favorite fav : this.favorites) {
            if (fav.getItemId().equals(itemId)) {
                return fav;
            }
        }
        return null;
    }

    public Favorite getFavoriteById(String id) {
        for (Favorite fav : this.favorites) {
            if (fav.getId().equals(id)) {
                return fav;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(final String attributeName) {
        return Lists.newArrayList(UserAttributes.stringValues()).contains(attributeName);
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    public void setPassword(String password) {
        validatePassword(password);
        this.password = password;
    }

    public boolean addFolder(Folder folder) {
        return folders.add(folder);
    }

    public boolean hasFolder(String folderId) {
        for (Folder folder : folders) {
            if (folder.getId().equals(folderId)) {
                return true;
            }
        }
        // not found
        return false;
    }

    public boolean hasGroup(String groupId) {
        for (Group group : groups) {
            if (group == null) {
                continue;
            }
            if (group.getId().equals(groupId)) {
                return true;
            }
        }
        // not found
        return false;
    }

    public Folder getFolder(String folderId) {
        for (Folder folder : folders) {
            if (folder.getId().equals(folderId)) {
                return folder;
            }
        }
        return null;
    }

    public static String fullName(final User user) {
        if (user == null) {
            return "null";
        }
        return format("%s %s", user.getFirstName(), user.getLastName()).trim();
    }
}
