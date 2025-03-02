package com.finlake.dao;

import com.finlake.common.enums.GlobalEnum;
import com.finlake.common.enums.ResponseCode;
import com.finlake.common.exception.InternalServerException;
import com.finlake.model.TransactionSplit;
import com.finlake.model.request.TransactionSplitDTO;
import com.finlake.model.response.TransactionSplitResponse;
import com.finlake.repository.TransactionSplitRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class TransactionSplitDaoImpl implements TransactionSplitDao {

    private final TransactionSplitRepository transactionSplitRepository;

    public TransactionSplitDaoImpl(TransactionSplitRepository transactionSplitRepository) {
        this.transactionSplitRepository = transactionSplitRepository;
    }

    @Override
    public List<TransactionSplit> getTransactionSplit(String transactionId, String requestId) {
        try {
            return transactionSplitRepository.getAllTransactionSplitsByTransactionId(transactionId);
        } catch (Exception e) {
            log.error("Exception {} during fetching transaction splits using transaction id {}", e, transactionId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    @Transactional
    public TransactionSplit createTransactionSplit(TransactionSplit transactionSplit) {
        try {
            return transactionSplitRepository.save(transactionSplit);
        } catch (Exception e) {
            log.error("Exception {} during fetching transaction splits using transaction id {} and request id {}", e, transactionSplit.getTransactionId(), transactionSplit.getRequestId());
            throw new InternalServerException(transactionSplit.getRequestId(), ResponseCode.DATABASE_ACCESS_ERROR);
        }
    }

    @Override
    @Transactional
    public TransactionSplit updateTransactionSplit(String requestId, String transactionSplitId) {
        try {
            Optional<TransactionSplit> transactionSplit = transactionSplitRepository.findById(transactionSplitId);
            if (transactionSplit.isEmpty()) {
                throw new InternalServerException(requestId, ResponseCode.DATA_DOES_NOT_EXIST);
            }
            TransactionSplit newTransactionSplit = transactionSplit.get();
            newTransactionSplit.setRequestId(requestId);
            newTransactionSplit.setStatus(GlobalEnum.STATUS_INACTIVE.getStringValue());
            return transactionSplitRepository.save(newTransactionSplit);
        } catch (Exception e) {
            log.error("Exception {} during settlement of a transaction split with transactionSplitId {} and request id {}", e, transactionSplitId, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        }
    }
}
