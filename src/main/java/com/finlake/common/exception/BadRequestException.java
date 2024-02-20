package com.finlake.common.exception;

import com.finlake.common.enums.ResponseCode;

public class BadRequestException extends BaseException {
    public BadRequestException(String requestId, ResponseCode errorCode) {
        super(requestId, errorCode.getCode());
    }
}
