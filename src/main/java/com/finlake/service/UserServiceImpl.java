package com.finlake.service;

import com.finlake.common.enums.ResponseCode;
import com.finlake.common.exception.DataConversionError;
import com.finlake.common.exception.UserNotFoundException;
import com.finlake.common.mapper.UserMapper;
import com.finlake.common.strategy.GetUserStrategy;
import com.finlake.common.strategy.UserRetrieveStrategyImpl;
import com.finlake.dao.UserDaoImpl;
import com.finlake.model.User;
import com.finlake.model.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserDaoImpl userDaoImpl;

    public UserServiceImpl(UserMapper userMapper, UserDaoImpl userDaoImpl) {
        this.userMapper = userMapper;
        this.userDaoImpl = userDaoImpl;
    }

    @Override
    public UserResponse findUser(String requestId, String id, String email, String mobileNumber) {
        UserRetrieveStrategyImpl userRetrieveStrategy = new UserRetrieveStrategyImpl(userDaoImpl);
        GetUserStrategy getUserStrategy = new GetUserStrategy(userRetrieveStrategy, email, mobileNumber, id, requestId);
        User user = Optional.ofNullable(getUserStrategy.getStrategy())
                .map(func -> func.apply(GetUserStrategy.getSearchValue(email, mobileNumber, id)))
                .orElseThrow(() -> new UserNotFoundException(requestId, ResponseCode.USER_DOES_NOT_EXIST));
        return getUserResponse(user, requestId);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable, String requestId) {
        Page<User> users = userDaoImpl.findAllUsers(pageable, requestId);
        return getUserListResponse(users, requestId);
    }

    @Override
    public Page<UserResponse> getAllUsersWithMobileNumber(List<String> mobileNumbers, String requestId, Pageable pageable) {
        Page<User> users = userDaoImpl.findAllUsersWithMobileNumber(mobileNumbers, requestId, pageable);
        return getUserListResponse(users, requestId);
    }

    @Override
    public Page<UserResponse> getAllUsersExceptSome(List<String> userIds, String requestId, Pageable pageable) {
        Page<User> users = userDaoImpl.findAllUsersExceptSome(userIds, requestId, pageable);
        return getUserListResponse(users, requestId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDaoImpl.findByEmail(email);
    }

    private Page<UserResponse> getUserListResponse(Page<User> users, String requestId) {
        try {
            return users.map(userMapper::mapToUserResponse);
        } catch (Exception e) {
            log.error("Exception {} occurred while mapping users to user responses with request id {}", e, requestId);
            throw new DataConversionError(requestId, ResponseCode.DATA_CONVERSION_ERROR);
        }
    }

    private UserResponse getUserResponse(User user, String requestId) {
        try {
            return userMapper.mapToUserResponse(user);
        } catch (Exception e) {
            log.error("Exception {} occurred while mapping users to user responses with request id {}", e, requestId);
            throw new DataConversionError(requestId, ResponseCode.DATA_CONVERSION_ERROR);
        }
    }
}
