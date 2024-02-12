package com.finlake.exception;

public class UserDoesNotExistsForEmailException extends BaseException {
    public UserDoesNotExistsForEmailException(String requestId, String responseCode) {
        super(requestId, responseCode);
    }
}
