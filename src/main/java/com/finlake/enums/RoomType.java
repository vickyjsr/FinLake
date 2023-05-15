package com.finlake.enums;

public enum RoomType {
    ONE_ON_ONE("one_on_one"),
    GROUP("group");

    private final String value;

    RoomType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
