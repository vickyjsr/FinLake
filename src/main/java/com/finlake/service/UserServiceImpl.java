package com.finlake.service;

import com.finlake.common.enums.ResponseCode;
import com.finlake.common.mapper.UserMapper;
import com.finlake.dao.UserDaoImpl;
import com.finlake.common.exception.DataConversionError;
import com.finlake.model.User;
import com.finlake.model.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Page<UserResponse> getAllUsers(Pageable pageable, String requestId) {
        Page<User> users = userDaoImpl.findAllUsers(pageable, requestId);
        return getUserResponse(users, requestId);
    }

    @Override
    public Page<UserResponse> getAllUsersWithMobileNumber(List<String> mobileNumbers, String requestId, Pageable pageable) {
        Page<User> users = userDaoImpl.findAllUsersWithMobileNumber(mobileNumbers, requestId, pageable);
        return getUserResponse(users, requestId);
    }

    @Override
    public Page<UserResponse> getAllUsersExceptSome(List<String> userIds, String requestId, Pageable pageable) {
        Page<User> users = userDaoImpl.findAllUsersExceptSome(userIds, requestId, pageable);
        return getUserResponse(users, requestId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDaoImpl.findByEmail(email);
    }

    private Page<UserResponse> getUserResponse(Page<User> users, String requestId) {
        try {
            return users.map(userMapper::mapToUserResponse);
        } catch (Exception e) {
            log.error("Exception {} occurred while mapping users to user responses with request id {}", e, requestId);
            throw new DataConversionError(requestId, ResponseCode.DATA_CONVERSION_ERROR);
        }
    }
}
