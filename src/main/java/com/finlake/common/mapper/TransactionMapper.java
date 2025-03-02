package com.finlake.common.mapper;

import com.finlake.common.enums.ResponseCode;
import com.finlake.common.enums.RoomType;
import com.finlake.common.exception.InternalServerException;
import com.finlake.model.FinanceRoom;
import com.finlake.model.Transaction;
import com.finlake.model.request.FinanceRoomRequestDTO;
import com.finlake.model.request.TransactionDTO;
import com.finlake.model.response.TransactionResponse;
import jakarta.persistence.Column;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", imports = {RoomType.class})
@Component
public abstract class TransactionMapper {

    @BeanMapping(qualifiedByName = "createTransactionFromTransactionRequestDTO", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "status", source = "status", defaultValue = "active")
    public abstract Transaction mapToTransaction(TransactionDTO transactionDTO);

    @Named("createTransactionFromTransactionRequestDTO")
    @AfterMapping
    public void createTransactionFromTransactionRequestDTO(TransactionDTO transactionDTO, @MappingTarget Transaction.TransactionBuilder transactionBuilder) {
        try {
            transactionBuilder.status(transactionDTO.getStatus());
            transactionBuilder.requestId(transactionDTO.getRequestId());
            transactionBuilder.amount(transactionDTO.getAmount());
            transactionBuilder.financeRoomId(transactionDTO.getFinanceRoomId());
            transactionBuilder.description(transactionDTO.getDescription());
            transactionBuilder.name(transactionDTO.getName());
            transactionBuilder.paidByUserId(transactionDTO.getPaidByUserId());
        } catch (Exception e) {
            throw new InternalServerException(transactionDTO.getRequestId(), ResponseCode.DATA_CONVERSION_ERROR);
        }
    }

    @BeanMapping(qualifiedByName = "createTransactionResponseFromTransaction", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract TransactionResponse mapToTransactionResponse(Transaction transaction);

    @Named("createTransactionResponseFromTransaction")
    @AfterMapping
    public void createTransactionResponseFromTransaction(Transaction transaction, @MappingTarget TransactionResponse transactionResponse) {
        try {
            transactionResponse.setId(transaction.getId());
            transactionResponse.setRequestId(transaction.getRequestId());
            transactionResponse.setName(transaction.getName());
            transactionResponse.setAmount(transaction.getAmount());
            transactionResponse.setDescription(transaction.getDescription());
            transactionResponse.setStatus(transaction.getStatus());
//            transactionResponse.setPaidByUser(transaction.getPaidByUserId());
            transactionResponse.setFinanceRoomId(transaction.getFinanceRoomId());
        } catch (Exception e) {
            throw new InternalServerException(transaction.getRequestId(), ResponseCode.DATA_CONVERSION_ERROR);
        }
    }
}
