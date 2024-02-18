package com.finlake.common.enums;

public enum GlobalEnum {
    STATUS_ACTIVE("active"),
    STATUS_INACTIVE("inactive");

    private final String value;

    GlobalEnum(String value) {
        this.value = value;
    }

    public String getStringValue() {
        return value;
    }
}
