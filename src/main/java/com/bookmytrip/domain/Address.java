package com.bookmytrip.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.google.common.collect.Lists;

@ToString
@ApiObject(name = "UserAddress")
public class Address extends AbstractDocument implements AttributesDocument {

    @Getter
    @Setter
    @ApiObjectField(description = "Description")
    private String description;

    @Getter
    @Setter
    @ApiObjectField(description = "Country")
    private String country;

    @Getter
    @Setter
    @ApiObjectField(description = "City")
    private String city;

    @Getter
    @Setter
    @ApiObjectField(description = "State")
    private String state;

    @Getter
    @Setter
    @ApiObjectField(description = "Postal Code")
    private String postalCode;

    @Getter
    @Setter
    @ApiObjectField(description = "Street Address (1)")
    private String street1;

    @Getter
    @Setter
    @ApiObjectField(description = "Street Address (2)")
    private String street2;


    public Address() {
    	//SK: fix this 
        //set(new ObjectId().toString());
        //setCreatedAt(new DateTime());
    }


    @Override
    public boolean containsKey(final String attributeName) {
        return Lists.newArrayList(UserAddressAttributes.stringValues()).contains(attributeName);
    }


}
