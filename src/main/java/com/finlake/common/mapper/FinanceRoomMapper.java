package com.finlake.common.mapper;

import com.finlake.common.enums.ResponseCode;
import com.finlake.common.enums.RoomType;
import com.finlake.common.exception.InternalServerException;
import com.finlake.model.FinanceRoom;
import com.finlake.model.request.FinanceRoomRequestDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class FinanceRoomMapper {

    @BeanMapping(qualifiedByName = "createFinanceRoom", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract FinanceRoom mapToFinanceRoom(FinanceRoomRequestDTO financeRoomRequestDTO);

    @Named("createFinanceRoomFromFinanceRoomRequestDTO")
    @AfterMapping
    public void createFinanceRoomFromFinanceRoomRequestDTO(FinanceRoomRequestDTO financeRoomRequestDTO, @MappingTarget FinanceRoom.FinanceRoomBuilder financeRoomBuilder) {
        try {
            financeRoomBuilder.financeRoomName(financeRoomRequestDTO.getFinanceRoomName());
            financeRoomBuilder.roomType(RoomType.getType(financeRoomRequestDTO.getRoomType()));
            financeRoomBuilder.createdBy(financeRoomRequestDTO.getCreatedBy());
            financeRoomBuilder.requestId(financeRoomRequestDTO.getRequestId());
        } catch (Exception e) {
            throw new InternalServerException(financeRoomRequestDTO.getRequestId(), ResponseCode.DATA_CONVERSION_ERROR);
        }
    }
}
