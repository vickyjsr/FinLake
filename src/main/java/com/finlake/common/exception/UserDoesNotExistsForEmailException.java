package com.finlake.common.exception;

import com.finlake.common.enums.ResponseCode;

public class UserDoesNotExistsForEmailException extends BaseException {
    public UserDoesNotExistsForEmailException(String requestId, ResponseCode responseCode) {
        super(requestId, responseCode.getCode());
    }
}
