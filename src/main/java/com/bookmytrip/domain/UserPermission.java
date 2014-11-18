package com.bookmytrip.domain;

import lombok.Getter;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.bookmytrip.SystemProperties.SysProps;

@ApiObject(name = "UserPermission")
public enum UserPermission {
    @ApiObjectField(description = "User has the ability to log into the portal")
    PORTAL_LOGIN ("User has the ability to log into the portal", SysProps.NEW_USER_CAN_ACCESS_PORTAL),
    @ApiObjectField(description = "User has the ability to upload assets from the portal")
    PORTAL_UPLOAD ("User has the ability to upload assets from the portal", SysProps.NEW_USER_CAN_UPLOAD_FROM_PORTAL),
    @ApiObjectField(description = "User has the ability to view and approve or reject uploads awaiting approval")
    UPLOAD_APPROVER ("User has the ability to view and approve or reject uploads awaiting approval",
            SysProps.NEW_USER_CAN_APPROVE_UPLOADS);

    @Getter
    private final String description;
    @Getter
    private final SysProps defaultSystemProperty;

    UserPermission(String description, SysProps defaultSystemProperty) {
        this.description = description;
        this.defaultSystemProperty = defaultSystemProperty;
    }
}
