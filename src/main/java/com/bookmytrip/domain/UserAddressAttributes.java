package com.bookmytrip.domain;

import java.util.ArrayList;
import java.util.List;

public enum UserAddressAttributes {

    TEMP_VALUE("t");

    private String name;

    UserAddressAttributes(final String name) {
        this.name = name;
    }

    public static List<String> stringValues() {
        List<String> vals = new ArrayList<>(values().length);
        for (UserAddressAttributes attr : values()) {
            vals.add(attr.name);
        }
        return vals;
    }

}
