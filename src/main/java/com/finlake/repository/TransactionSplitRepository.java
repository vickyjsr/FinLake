package com.finlake.repository;

import com.finlake.model.TransactionSplit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionSplitRepository extends JpaRepository<TransactionSplit, String> {

}
