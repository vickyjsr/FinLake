package com.finlake.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    INTERNAL_SERVER_EXCEPTION("1000"),
    BAD_REQUEST("1001"),
    USER_REGISTERED("1002"),
    USER_AUTHORIZED("1003"),
    INVALID_JWT_AUTHENTICATION("1004"),
    DATABASE_ACCESS_ERROR("1005"),
    DATA_CONVERSION_ERROR("1006"),

    ALL_USER_FETCHED("2002"),
    USER_ALREADY_EXISTS("2003"),
    USER_DOES_NOT_EXIST("2004"),
    FINANCE_ROOM_CREATED("2005"),
    FINANCE_ROOM_FETCHED("2005"),
    ROOM_USER_CREATED("2006"),
    ROOM_USER_FETCHED("2007"),

    UNKNOWN("9999");

    private final String code;
}
