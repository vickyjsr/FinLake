package com.finlake.common.exception;

import com.finlake.common.enums.ResponseCode;

public class DataConversionError extends BaseException {
    public DataConversionError(String requestId, ResponseCode errorCode) {
        super(requestId, errorCode.getCode());
    }
}
