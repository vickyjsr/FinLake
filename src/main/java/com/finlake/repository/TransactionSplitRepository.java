package com.finlake.repository;

import com.finlake.model.TransactionSplit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionSplitRepository extends JpaRepository<TransactionSplit, String> {
    List<TransactionSplit> getAllTransactionSplitsByTransactionId(String transactionId);
}
