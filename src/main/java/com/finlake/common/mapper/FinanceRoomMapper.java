package com.finlake.common.mapper;

import com.finlake.common.enums.GlobalEnum;
import com.finlake.common.enums.ResponseCode;
import com.finlake.common.enums.RoomType;
import com.finlake.common.exception.InternalServerException;
import com.finlake.model.FinanceRoom;
import com.finlake.model.request.FinanceRoomRequestDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", imports = {RoomType.class})
@Component
public abstract class FinanceRoomMapper {

    @BeanMapping(qualifiedByName = "createFinanceRoomFromFinanceRoomRequestDTO", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roomType", expression = "java(RoomType.getType(financeRoomRequestDTO.getRoomType()))")
    @Mapping(target = "financeRoomName", source = "financeRoomName")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "requestId", source = "requestId")
    @Mapping(target = "status", source = "status", defaultValue = "active")
    public abstract FinanceRoom mapToFinanceRoom(FinanceRoomRequestDTO financeRoomRequestDTO);

    @Named("createFinanceRoomFromFinanceRoomRequestDTO")
    @AfterMapping
    public void createFinanceRoomFromFinanceRoomRequestDTO(FinanceRoomRequestDTO financeRoomRequestDTO, @MappingTarget FinanceRoom financeRoomBuilder) {
        try {
            financeRoomBuilder.setFinanceRoomName(financeRoomRequestDTO.getFinanceRoomName());
            financeRoomBuilder.setRoomType(RoomType.getType(financeRoomRequestDTO.getRoomType()));
            financeRoomBuilder.setCreatedBy(financeRoomRequestDTO.getCreatedBy());
            financeRoomBuilder.setRequestId(financeRoomRequestDTO.getRequestId());
            if (financeRoomRequestDTO.getStatus() != null) {
                financeRoomBuilder.setStatus(financeRoomRequestDTO.getStatus());
            } else {
                financeRoomBuilder.setStatus("active");
            }
        } catch (Exception e) {
            throw new InternalServerException(financeRoomRequestDTO.getRequestId(), ResponseCode.DATA_CONVERSION_ERROR);
        }
    }
}
