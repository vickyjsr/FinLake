package com.finlake.service;

import com.finlake.model.request.TransactionSplitDTO;
import com.finlake.model.response.TransactionSplitResponse;

import java.util.List;

public interface TransactionSplitService {
    List<TransactionSplitResponse> getTransactionSplit(String transactionId, String requestId);
    List<TransactionSplitResponse> getTransactionSplitById(String transactionId, String requestId);
    TransactionSplitResponse createTransactionSplit(TransactionSplitDTO transactionSplitDTO);
    TransactionSplitResponse updateTransactionSplit(String requestId, String transactionSplitId);
}
