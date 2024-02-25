package com.finlake.common.mapper;

import com.finlake.model.RoomUser;
import com.finlake.model.request.RoomUserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public abstract class RoomUserMapper {

    @BeanMapping(qualifiedByName = "createRoomUser", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract RoomUser mapToRoomUser(RoomUserDTO roomUserDTO);
}
