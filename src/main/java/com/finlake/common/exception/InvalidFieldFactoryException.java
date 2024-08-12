package com.finlake.common.exception;

import com.finlake.common.enums.ResponseCode;

public class InvalidFieldFactoryException extends BaseException {
    public InvalidFieldFactoryException(String requestId, ResponseCode errorCode) {
        super(requestId, errorCode.getCode());
    }
}
