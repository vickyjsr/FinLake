package com.finlake.exception;

import com.finlake.enums.ResponseCode;

public class InvalidJWTException extends BaseException {

    public InvalidJWTException(String requestId, ResponseCode responseCode) {
        super(requestId, responseCode.getCode());
    }
}
