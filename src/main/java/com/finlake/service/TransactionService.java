package com.finlake.service;

import com.finlake.model.request.TransactionDTO;
import com.finlake.model.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    public TransactionResponse createTransactionRecord(TransactionDTO transactionDTO);
    public TransactionResponse updateTransactionRecord(String transactionId, String status, String requestId);
    public TransactionResponse deleteTransactionRecord(String transactionId);
    public List<TransactionResponse> getAllTransactionRecordByFinanceRoomId(String financeRoomId, String requestId);
}
