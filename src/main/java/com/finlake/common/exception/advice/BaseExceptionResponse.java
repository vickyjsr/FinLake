package com.finlake.common.exception.advice;

import com.finlake.common.cache.ResponseMapperCache;
import com.finlake.model.ResponseMapper;
import com.finlake.model.response.FinlakeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BaseExceptionResponse {

    private final ResponseMapperCache responseMapperCache;

    public BaseExceptionResponse(ResponseMapperCache responseMapperCache) {
        this.responseMapperCache = responseMapperCache;
    }

    public ResponseEntity<Object> getErrorResponse(String requestId, String errorCode, String errorMessage) {
        ResponseMapper responseMapper = responseMapperCache.lookUpByResponseCode(errorCode);
        log.info("Response {} received for request id {}", errorCode, requestId);
        FinlakeResponse<?> body = getBody(requestId, errorMessage, responseMapper);
        return switch (responseMapper.getHttpStatusCode()) {
            case "200" -> ResponseEntity.ok().body(body);
            case "400" -> ResponseEntity.badRequest().body(body);
            case "401" -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
            default -> ResponseEntity.internalServerError().body(body);
        };
    }

    private FinlakeResponse<?> getBody(String requestId, String errorMessage, ResponseMapper responseMapper) {
        FinlakeResponse<?> finlakeResponse = new FinlakeResponse<>();
        finlakeResponse.setRequestId(requestId);
        finlakeResponse.setResponseMessage(errorMessage == null ? responseMapper.getResponseMessage() : errorMessage);
        finlakeResponse.setResponseCode(responseMapper.getResponseCode());
        return finlakeResponse;
    }
}
