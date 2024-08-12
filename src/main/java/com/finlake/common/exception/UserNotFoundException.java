package com.finlake.common.exception;

import com.finlake.common.enums.ResponseCode;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String requestId, ResponseCode responseCode) {
        super(requestId, responseCode.getCode());
    }
}
