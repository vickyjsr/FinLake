package com.finlake.common.cache;

import com.finlake.dao.ResponseMapperDao;
import com.finlake.model.ResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class ResponseMapperCache {

    private final ResponseMapperDao responseMapperDao;

    private Map<String, ResponseMapper> responseCodeCache = new HashMap<>();

    public ResponseMapperCache(ResponseMapperDao responseMapperDao) {
        this.responseMapperDao = responseMapperDao;
    }

    public ResponseMapper lookUpByResponseCode(String responseCode) {
        try {
            ResponseMapper responseMapper = responseCodeCache.get(responseCode);
            if (responseMapper == null) {
                responseMapper = getFromDbOrDefault(responseCode);
            }
            return responseMapper;
        } catch (Exception e) {
            log.error("Exception {} while fetching response code {}", e, responseCode);
            return new ResponseMapper().getDefaultResponse();
        }
    }

    private ResponseMapper getFromDbOrDefault(String responseCode) {
        CompletableFuture.runAsync(this::refresh);
        Optional<ResponseMapper> responseMapper = responseMapperDao.findByResponseCode(responseCode);
        return responseMapper.orElseGet(() -> new ResponseMapper().getDefaultResponse());
    }

    protected void refresh() {
        try {
            Map<String, ResponseMapper> localResponseCodeCache = new HashMap<>();
            List<ResponseMapper> responseMappers = responseMapperDao.findAll();
            if (!CollectionUtils.isEmpty(responseMappers)) {
                responseMappers.forEach(responseMapper -> localResponseCodeCache.put(responseMapper.getResponseCode(), responseMapper));
                this.responseCodeCache = localResponseCodeCache;
            }
        } catch (Exception e) {
            log.error("Exception {} occurred while fetching response mapper codes", e);
            throw e;
        }
    }
}
