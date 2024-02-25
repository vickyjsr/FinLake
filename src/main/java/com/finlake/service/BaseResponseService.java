package com.finlake.service;

import com.finlake.common.enums.ResponseCode;
import com.finlake.model.response.FinlakeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BaseResponseService {

    public <T> ResponseEntity<FinlakeResponse<T>> ok(T response, String requestId, String responseCode) {
        FinlakeResponse<T> finlakeResponse = new FinlakeResponse<>();
        finlakeResponse.setData(response);
        finlakeResponse.setResponseCode(responseCode);
        finlakeResponse.setRequestId(requestId);
        return ResponseEntity.ok().body(finlakeResponse);
    }
}
