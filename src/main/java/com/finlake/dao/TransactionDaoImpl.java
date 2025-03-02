package com.finlake.dao;

import com.finlake.common.enums.ResponseCode;
import com.finlake.common.exception.DataConversionError;
import com.finlake.common.exception.InternalServerException;
import com.finlake.common.mapper.TransactionMapper;
import com.finlake.model.Transaction;
import com.finlake.model.response.TransactionResponse;
import com.finlake.model.response.UserResponse;
import com.finlake.repository.TransactionRepository;
import com.finlake.service.UserServiceImpl;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class TransactionDaoImpl implements TransactionDao {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserServiceImpl userServiceImpl;

    public TransactionDaoImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, UserServiceImpl userServiceImpl) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public List<TransactionResponse> getTransactionByFinanceRoomId(String financeRoomId, String requestId) {
        List<Transaction> transactions;
        try {
            transactions = transactionRepository.findAllByFinanceRoomId(financeRoomId);
        } catch (Exception e) {
            log.error("Exception {} occurred while fetching transaction by finance room id with request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        }
        try {
            List<TransactionResponse> transactionResponses = new ArrayList<>();
            for (Transaction transaction : transactions) {
                TransactionResponse transactionResponse = transactionMapper.mapToTransactionResponse(transaction);
                UserResponse userResponse = userServiceImpl.findUser(requestId, transaction.getPaidByUserId(), null, null);
                transactionResponse.setPaidByUser(userResponse);
                transactionResponses.add(transactionResponse);
            }
            return transactionResponses;
        } catch (Exception e) {
            log.error("Exception {} occurred while converting transaction to transaction response with request id {}", e, requestId);
            throw new DataConversionError(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    @Transactional
    public TransactionResponse createTransaction(Transaction createTransaction) {
        Transaction transaction;
        try {
            transaction = transactionRepository.save(createTransaction);
        } catch (Exception e) {
            log.error("Exception {} occurred while saving transaction with request id {}", e, createTransaction.getRequestId());
            throw new InternalServerException(createTransaction.getRequestId(), ResponseCode.DATABASE_ACCESS_ERROR);
        }
        try {
            return transactionMapper.mapToTransactionResponse(transaction);
        } catch (Exception e) {
            log.error("Exception {} occurred while converting transaction to transaction response with request id {}", e, createTransaction.getRequestId());
            throw new InternalServerException(createTransaction.getRequestId(), ResponseCode.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    @Transactional
    public Transaction updateTransaction(String transactionId, String status, String requestId) {
        try {
            Optional<Transaction> transaction = transactionRepository.findById(transactionId);
            if (transaction.isPresent()) {
                Transaction newTransaction = transaction.get();
                newTransaction.setRequestId(requestId);
                newTransaction.setStatus(status);
                return transactionRepository.save(newTransaction);
            } else {
                throw new InternalServerException(requestId, ResponseCode.DATA_DOES_NOT_EXIST);
            }
        } catch (Exception e) {
            log.error("Exception {} occurred while updating transaction with request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        }
    }
}
