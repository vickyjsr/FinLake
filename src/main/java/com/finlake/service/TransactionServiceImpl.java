package com.finlake.service;

import com.finlake.common.enums.GlobalEnum;
import com.finlake.common.enums.ResponseCode;
import com.finlake.common.exception.DataConversionError;
import com.finlake.common.exception.InternalServerException;
import com.finlake.common.mapper.TransactionMapper;
import com.finlake.dao.TransactionDaoImpl;
import com.finlake.model.Transaction;
import com.finlake.model.request.TransactionDTO;
import com.finlake.model.request.TransactionSplitDTO;
import com.finlake.model.response.TransactionResponse;
import com.finlake.model.response.TransactionSplitResponse;
import com.finlake.model.response.UserResponse;
import com.finlake.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionDaoImpl transactionDao;
    private final TransactionSplitService transactionSplitService;
    private final UserServiceImpl userServiceImpl;

    public TransactionServiceImpl(TransactionMapper transactionMapper, TransactionDaoImpl transactionDao, TransactionSplitService transactionSplitService, UserServiceImpl userServiceImpl) {
        this.transactionMapper = transactionMapper;
        this.transactionDao = transactionDao;
        this.transactionSplitService = transactionSplitService;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    @Transactional
    public TransactionResponse createTransactionRecord(TransactionDTO transactionDTO) {
        Transaction transaction = null;
        try {
            transaction = transactionMapper.mapToTransaction(transactionDTO);
        } catch (Exception e) {
            log.error("Exception {} occurred while mapping transactionDto to transaction entity with request id {}", e, transactionDTO.getRequestId());
            throw new DataConversionError(transactionDTO.getRequestId(), ResponseCode.DATA_CONVERSION_ERROR);
        }
        try {
            TransactionResponse transactionResponse = transactionDao.createTransaction(transaction);
            final List<TransactionSplitResponse> transactionSplitResponses = getTransactionSplitResponses(transactionDTO, transactionResponse);
            UserResponse paidByUser = userServiceImpl.findUser(transactionDTO.getRequestId(), transaction.getPaidByUserId(), null, null);
            transactionResponse.setPaidByUser(paidByUser);
            transactionResponse.setSplits(transactionSplitResponses);
            return transactionResponse;
        } catch (InternalServerException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception {} occurred while saving transaction entity with request id {}", e, transactionDTO.getRequestId());
            throw new InternalServerException(transactionDTO.getRequestId(), ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    private List<TransactionSplitResponse> getTransactionSplitResponses(TransactionDTO transactionDTO, TransactionResponse transactionResponse) {
        List<TransactionSplitResponse> transactionSplitResponses = new ArrayList<>(List.of());
        List<TransactionSplitDTO> transactionSplitDTOS = transactionDTO.getSplit();
        transactionSplitDTOS.forEach(transactionSplitDTO -> {
            transactionSplitDTO.setTransactionId(transactionResponse.getId());
            transactionSplitDTO.setStatus(GlobalEnum.STATUS_ACTIVE.getStringValue());
            transactionSplitResponses.add(transactionSplitService.createTransactionSplit(transactionSplitDTO));
        });
        return transactionSplitResponses;
    }

    @Override
    @Transactional
    public TransactionResponse updateTransactionRecord(String transactionId, String status, String requestId) {
        try {
            Transaction transaction = transactionDao.updateTransaction(transactionId, status, requestId);
            return transactionMapper.mapToTransactionResponse(transaction);
        } catch (InternalServerException e) {
            throw e;
        }catch (Exception e) {
            throw new InternalServerException(transactionId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    @Override
    public TransactionResponse deleteTransactionRecord(String transactionId) {
        return null;
    }

    @Override
    public List<TransactionResponse> getAllTransactionRecordByFinanceRoomId(String financeRoomId, String requestId) {
        try {
            List<TransactionResponse> transactionResponses = transactionDao.getTransactionByFinanceRoomId(financeRoomId, requestId);
            transactionResponses.forEach(transactionResponse -> {
                List<TransactionSplitResponse> transactionSplitResponses = transactionSplitService.getTransactionSplitById(transactionResponse.getId(), requestId);
                transactionResponse.setSplits(transactionSplitResponses);
            });
            return transactionResponses;
        } catch (Exception e) {
            log.error("Exception {} occurred while fetching transactions entities with request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }
}
