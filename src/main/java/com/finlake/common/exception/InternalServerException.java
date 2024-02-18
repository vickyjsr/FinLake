package com.finlake.common.exception;

import com.finlake.common.enums.ResponseCode;

public class InternalServerException extends BaseException {
    public InternalServerException(String requestId, ResponseCode errorCode) {
        super(requestId, errorCode.getCode());
    }
}
