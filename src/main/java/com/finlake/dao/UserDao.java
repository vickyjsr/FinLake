package com.finlake.dao;

import com.finlake.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDao {

    Page<User> findAllUsers(Pageable pageable, String requestId);

    Page<User> findAllUsersWithMobileNumber(List<String> mobileNumbers, String requestId, Pageable pageable);

    Page<User> findAllUsersExceptSome(List<String> userIds, String requestId, Pageable pageable);

    User findByEmail(String email);
}
