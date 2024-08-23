package com.finlake.common.enums;

public enum SplitType {
    EQUAL("equal"), UNEQUAL("unequal"), PERCENTAGE("percentage");

    private final String value;

    SplitType(String value) {
        this.value = value;
    }

    public static SplitType getSplitType(String splitType) {
        if (splitType.equalsIgnoreCase("equal")) {
            return EQUAL;
        } else if (splitType.equalsIgnoreCase("unequal")) {
            return UNEQUAL;
        } else {
            return PERCENTAGE;
        }
    }

    public String getValue() {
        return value;
    }
}
