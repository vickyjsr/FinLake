package com.finlake.common.enums;

public enum RoleType {

    USER("USER"),
    ADMIN("ADMIN");

    private final String value;

    RoleType(String value) {
        this.value = value;
    }

    public String getStringValue() {
        return value;
    }
}
