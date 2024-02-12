package com.finlake.dao;

import com.finlake.model.ResponseMapper;

import java.util.List;
import java.util.Optional;

public interface ResponseMapperDao {

    Optional<ResponseMapper> findByResponseCode(String responseCode);

    List<ResponseMapper> findAll();
}
