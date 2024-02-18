package com.finlake.common.mapper;

import com.finlake.model.User;
import com.finlake.model.response.UserResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @BeanMapping(qualifiedByName = "createUserResponseDto", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract UserResponse mapToUserResponse(User user);

/**        this can be helpful when we deal with something like encrypted values which should be decrypted after automatic mapping
    @Named("createUserResponseDto")
    @AfterMapping
    public void createUserResponseDto(User user, @MappingTarget UserResponse.UserResponseBuilder userResponse) {
        try {
            userResponse.createdAt(user.getCreatedAt());
        } catch (Exception e) {
            throw e;
        }
    }
 */

}
