package com.finlake.common.exception;

public class UserDoesNotExistsForEmailException extends BaseException {
    public UserDoesNotExistsForEmailException(String requestId, String responseCode) {
        super(requestId, responseCode);
    }
}
