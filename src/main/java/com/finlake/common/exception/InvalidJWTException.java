package com.finlake.common.exception;

import com.finlake.common.enums.ResponseCode;

public class InvalidJWTException extends BaseException {

    public InvalidJWTException(String requestId, ResponseCode responseCode) {
        super(requestId, responseCode.getCode());
    }
}
