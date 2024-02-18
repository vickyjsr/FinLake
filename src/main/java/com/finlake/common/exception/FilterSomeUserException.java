package com.finlake.common.exception;

import com.finlake.common.enums.ResponseCode;

public class FilterSomeUserException extends BaseException {
    public FilterSomeUserException(String requestId, ResponseCode errorCode) {
        super(requestId, errorCode.getCode());
    }
}
