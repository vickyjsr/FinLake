package com.finlake.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    private String requestId;
    private String errorCode;
    private String errorMessage;

    public BaseException(String requestId, String errorCode) {
        this.requestId = requestId;
        this.errorCode = errorCode;
    }

}
