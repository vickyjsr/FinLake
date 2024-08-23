package com.finlake.common.enums;

public enum RoomType {
    ONE_ON_ONE("one_on_one"),
    GROUP("group");

    private final String value;

    RoomType(String value) {
        this.value = value;
    }

    public static RoomType getType(String roomType) {
        if (roomType.equalsIgnoreCase("one_on_one")) {
            return ONE_ON_ONE;
        } else {
            return GROUP;
        }
    }

    public String getValue() {
        return value;
    }
}
