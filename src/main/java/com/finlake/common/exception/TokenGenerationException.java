package com.finlake.common.exception;

import com.finlake.common.enums.ResponseCode;

public class TokenGenerationException extends BaseException {
    public TokenGenerationException(String requestId, ResponseCode responseCode) {
        super(requestId, responseCode.getCode());
    }
}
