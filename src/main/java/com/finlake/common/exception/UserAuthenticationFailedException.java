package com.finlake.common.exception;

import com.finlake.common.enums.ResponseCode;

public class UserAuthenticationFailedException extends BaseException {
    public UserAuthenticationFailedException(String requestId, ResponseCode errorCode) {
        super(requestId, errorCode.getCode());
    }
}
