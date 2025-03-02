package com.finlake.common.mapper;

import com.finlake.common.enums.GlobalEnum;
import com.finlake.common.enums.ResponseCode;
import com.finlake.common.enums.RoomType;
import com.finlake.common.exception.InternalServerException;
import com.finlake.model.Transaction;
import com.finlake.model.TransactionSplit;
import com.finlake.model.request.TransactionDTO;
import com.finlake.model.request.TransactionSplitDTO;
import com.finlake.model.response.TransactionSplitResponse;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", imports = {RoomType.class})
@Component
public abstract class TransactionSplitMapper {

    @BeanMapping(qualifiedByName = "createTransactionSplitFromTransactionSplitDTO", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "status", source = "status", defaultValue = "active")
    public abstract TransactionSplit mapToTransactionSplit(TransactionSplitDTO transactionDTO);

    @Named("createTransactionSplitFromTransactionSplitDTO")
    @AfterMapping
    public void createTransactionSplitFromTransactionSplitDTO(TransactionSplitDTO transactionSplitDTO, @MappingTarget TransactionSplit.TransactionSplitBuilder transactionSplitBuilder) {
        try {
            transactionSplitBuilder.requestId(transactionSplitDTO.getRequestId());
            transactionSplitBuilder.amount(transactionSplitDTO.getAmount());
            transactionSplitBuilder.transactionId(transactionSplitDTO.getTransactionId());
            transactionSplitBuilder.splitPercent(transactionSplitDTO.getSplitPercent());
            transactionSplitBuilder.paidByUserId(transactionSplitDTO.getPaidByUserId());
            transactionSplitBuilder.receivedByUserId(transactionSplitDTO.getReceivedByUserId());
            if (transactionSplitDTO.getPaidByUserId().equals(transactionSplitDTO.getReceivedByUserId())) {
                transactionSplitBuilder.status(GlobalEnum.STATUS_INACTIVE.getStringValue());
            } else {
                transactionSplitBuilder.status(transactionSplitDTO.getStatus());
            }
        } catch (Exception e) {
            throw new InternalServerException(transactionSplitDTO.getRequestId(), ResponseCode.DATA_CONVERSION_ERROR);
        }
    }

    @BeanMapping(qualifiedByName = "createTransactionSplitResponseFromTransactionSplit", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "status", source = "status", defaultValue = "active")
    public abstract TransactionSplitResponse mapToTransactionSplitResponse(TransactionSplit transactionSplit);

    @Named("createTransactionSplitFromTransactionSplitDTO")
    @AfterMapping
    public void createTransactionSplitResponseFromTransactionSplit(TransactionSplit transactionSplit, @MappingTarget TransactionSplitResponse.TransactionSplitResponseBuilder transactionSplitResponseBuilder) {
        try {
            transactionSplitResponseBuilder.status(transactionSplit.getStatus());
            transactionSplitResponseBuilder.requestId(transactionSplit.getRequestId());
            transactionSplitResponseBuilder.amount(transactionSplit.getAmount());
            transactionSplitResponseBuilder.transactionId(transactionSplit.getTransactionId());
            transactionSplitResponseBuilder.splitPercent(transactionSplit.getSplitPercent());
//            transactionSplitResponseBuilder.paidByUserId(transactionSplit.getPaidByUserId());
//            transactionSplitResponseBuilder.receivedByUserId(transactionSplit.getReceivedByUserId());
        } catch (Exception e) {
            throw new InternalServerException(transactionSplit.getRequestId(), ResponseCode.DATA_CONVERSION_ERROR);
        }
    }

}
