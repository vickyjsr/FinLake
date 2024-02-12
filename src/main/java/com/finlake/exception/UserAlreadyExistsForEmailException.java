package com.finlake.exception;

import com.finlake.enums.ResponseCode;

/**
 * Created by vickyjsr (Gourav Modi)
 */
public class UserAlreadyExistsForEmailException extends BaseException {
    public UserAlreadyExistsForEmailException(String requestId, ResponseCode responseCode) {
        super(requestId, responseCode.getCode());
    }
}
