package com.finlake.common.exception;

import com.finlake.common.enums.ResponseCode;

/**
 * Created by vickyjsr (Gourav Modi)
 */
public class UserAlreadyExistsForEmailException extends BaseException {
    public UserAlreadyExistsForEmailException(String requestId, ResponseCode responseCode) {
        super(requestId, responseCode.getCode());
    }
}
