package com.finlake.service;

import com.finlake.model.User;
import com.finlake.model.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<UserResponse> getAllUsers(Pageable pageable, String requestId);

    Page<UserResponse> getAllUsersWithMobileNumber(List<String> mobileNumbers, String requestId, Pageable pageable);

    Page<UserResponse> getAllUsersExceptSome(List<String> userIds, String requestId, Pageable pageable);

    User getUserByEmail(String email);

    UserResponse findUser(String requestId, String id, String email, String mobileNumber);
}
