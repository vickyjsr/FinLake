package com.finlake.dao;

import com.finlake.model.ResponseMapper;
import com.finlake.repository.ResponseMapperRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ResponseMapperDaoImpl implements ResponseMapperDao {

    private final ResponseMapperRepository responseMapperRepository;

    public ResponseMapperDaoImpl(ResponseMapperRepository responseMapperRepository) {
        this.responseMapperRepository = responseMapperRepository;
    }

    @Override
    public Optional<ResponseMapper> findByResponseCode(String responseCode) {
        try {
            return responseMapperRepository.findByResponseCode(responseCode);
        } catch (Exception e) {
            log.error("Error {} while accessing database while fetching response mapper", e);
            return Optional.empty();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseMapper> findAll() {
        return responseMapperRepository.findAll();
    }
}
