package com.finlake.repository;

import com.finlake.model.ResponseMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ResponseMapperRepository extends JpaRepository<ResponseMapper, Long> {
    @Transactional(readOnly = true)
    Optional<ResponseMapper> findByResponseCode(String responseCode);
}
