package com.finlake.service;

import com.finlake.common.enums.GlobalEnum;
import com.finlake.common.enums.ResponseCode;
import com.finlake.common.exception.DataConversionError;
import com.finlake.common.exception.InternalServerException;
import com.finlake.common.mapper.TransactionSplitMapper;
import com.finlake.dao.TransactionSplitDaoImpl;
import com.finlake.model.TransactionSplit;
import com.finlake.model.request.TransactionSplitDTO;
import com.finlake.model.response.TransactionResponse;
import com.finlake.model.response.TransactionSplitResponse;
import com.finlake.model.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TransactionSplitServiceImpl implements TransactionSplitService {

    private final TransactionSplitDaoImpl transactionSplitDao;
    private final TransactionSplitMapper transactionSplitMapper;
    private final UserServiceImpl userServiceImpl;
    private final TransactionService transactionService;

    public TransactionSplitServiceImpl(TransactionSplitDaoImpl transactionSplitDao, TransactionSplitMapper transactionSplitMapper, UserServiceImpl userServiceImpl, TransactionService transactionService) {
        this.transactionSplitDao = transactionSplitDao;
        this.transactionSplitMapper = transactionSplitMapper;
        this.userServiceImpl = userServiceImpl;
        this.transactionService = transactionService;
    }

    @Override
    public List<TransactionSplitResponse> getTransactionSplit(String transactionId, String requestId) {
        try {
            List<TransactionSplit> transactionSplits = transactionSplitDao.getTransactionSplit(transactionId, requestId);
            List<TransactionSplitResponse> transactionSplitResponseList = new ArrayList<>();
            transactionSplits.forEach(transactionSplit -> {
                transactionSplitResponseList.add(convertSplitToResponse(transactionSplit));
            });
            return transactionSplitResponseList;
        } catch (InternalServerException | DataConversionError e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(transactionId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    @Override
    public List<TransactionSplitResponse> getTransactionSplitById(String transactionId, String requestId) {
        try {
            List<TransactionSplit> transactionSplits = transactionSplitDao.getTransactionSplit(transactionId, requestId);
            List<TransactionSplitResponse> transactionSplitResponseList = new ArrayList<>();
            transactionSplits.forEach(transactionSplit -> {
                transactionSplitResponseList.add(convertSplitToResponse(transactionSplit));
            });
            return transactionSplitResponseList;
        } catch (InternalServerException | DataConversionError e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(transactionId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public TransactionSplitResponse createTransactionSplit(TransactionSplitDTO transactionSplitDTO) {
        TransactionSplit transactionSplit;
        try {
            transactionSplit = transactionSplitMapper.mapToTransactionSplit(transactionSplitDTO);
        } catch (Exception e) {
            log.error("Exception {} occured while creating transaction split with request id {}", e, transactionSplitDTO.getTransactionId());
            throw new DataConversionError(transactionSplitDTO.getRequestId(), ResponseCode.DATA_CONVERSION_ERROR);
        }
        try {
            TransactionSplit newTransactionSplit = transactionSplitDao.createTransactionSplit(transactionSplit);
            UserResponse paidByUser = userServiceImpl.findUser(transactionSplitDTO.getRequestId(), transactionSplitDTO.getPaidByUserId(), null, null);
            UserResponse receivedByUser = userServiceImpl.findUser(transactionSplitDTO.getRequestId(), transactionSplitDTO.getReceivedByUserId(), null, null);
            TransactionSplitResponse transactionSplitResponse = transactionSplitMapper.mapToTransactionSplitResponse(newTransactionSplit);
            transactionSplitResponse.setPaidByUser(paidByUser);
            transactionSplitResponse.setReceivedByUser(receivedByUser);
            return transactionSplitResponse;
        } catch (InternalServerException e) {
            throw e;
        } catch (Exception e) {
            log.error("Exception {} occured while creating transaction split with request id {}", e, transactionSplitDTO.getRequestId());
            throw new DataConversionError(transactionSplitDTO.getRequestId(), ResponseCode.DATA_CONVERSION_ERROR);
        }
    }

    @Override
    @Transactional
    public TransactionSplitResponse updateTransactionSplit(String requestId, String transactionSplitId) {
        try {
            TransactionSplit transactionSplit = transactionSplitDao.updateTransactionSplit(requestId, transactionSplitId);
            List<TransactionSplit> transactionSplits = transactionSplitDao.getTransactionSplit(transactionSplit.getTransactionId(), requestId);
            checkStatusUpdateForAllSplitsAndUpdateParentRecord(transactionSplits, transactionSplit.getTransactionId(), requestId);
            return convertSplitToResponse(transactionSplit);
        } catch (InternalServerException | DataConversionError e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(requestId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    private void checkStatusUpdateForAllSplitsAndUpdateParentRecord(List<TransactionSplit> transactionSplits, String transactionId, String requestId) {
        boolean parentSettlementDone = false;
        for (TransactionSplit transactionSplit : transactionSplits) {
            if (transactionSplit.getStatus().equals("active")) {
                parentSettlementDone = true;
                break;
            }
        }
        if (parentSettlementDone) {
            TransactionResponse transactionResponse = transactionService.updateTransactionRecord(transactionId, GlobalEnum.STATUS_INACTIVE.getStringValue(), requestId);
        }
    }

    private TransactionSplitResponse convertSplitToResponse(TransactionSplit transactionSplit) {
        try {
            TransactionSplitResponse transactionSplitResponse = transactionSplitMapper.mapToTransactionSplitResponse(transactionSplit);
            UserResponse paidByUser = userServiceImpl.findUser(transactionSplit.getRequestId(), transactionSplit.getPaidByUserId(), null, null);
            UserResponse receivedByUser = userServiceImpl.findUser(transactionSplit.getRequestId(), transactionSplit.getReceivedByUserId(), null, null);
            transactionSplitResponse.setPaidByUser(paidByUser);
            transactionSplitResponse.setReceivedByUser(receivedByUser);
            transactionSplitResponse.setTransactionSplitId(transactionSplit.getId());
            return transactionSplitResponse;
        } catch (Exception e) {
            throw new DataConversionError(transactionSplit.getRequestId(), ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }
}
