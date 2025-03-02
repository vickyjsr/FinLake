package com.finlake.dao;

import com.finlake.model.TransactionSplit;
import com.finlake.model.request.TransactionSplitDTO;
import com.finlake.model.response.TransactionSplitResponse;

import java.util.List;

public interface TransactionSplitDao {
    List<TransactionSplit> getTransactionSplit(String transactionId, String requestId);
    TransactionSplit createTransactionSplit(TransactionSplit transactionSplit);
    TransactionSplit updateTransactionSplit(String requestId, String transactionSplitId);
}
