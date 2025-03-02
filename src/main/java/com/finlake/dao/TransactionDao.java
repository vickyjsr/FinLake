package com.finlake.dao;

import com.finlake.model.Transaction;
import com.finlake.model.request.FinanceRoomRequestDTO;
import com.finlake.model.response.TransactionResponse;

import java.util.List;

public interface TransactionDao {
    List<TransactionResponse> getTransactionByFinanceRoomId(String financeRoomId, String requestId);
    TransactionResponse createTransaction(Transaction transaction);
    Transaction updateTransaction(String transactionId, String status, String requestId);
}
