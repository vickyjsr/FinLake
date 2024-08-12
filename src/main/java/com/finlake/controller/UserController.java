package com.finlake.controller;

import com.finlake.common.enums.ResponseCode;
import com.finlake.model.response.FinlakeResponse;
import com.finlake.model.response.UserResponse;
import com.finlake.service.BaseResponseService;
import com.finlake.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "2. User Controller")
public class UserController implements UserControllerApi {

    private final UserServiceImpl userService;
    private final BaseResponseService baseResponseService;

    public UserController(UserServiceImpl userService, BaseResponseService baseResponseService) {
        this.userService = userService;
        this.baseResponseService = baseResponseService;
    }

    @Override
    public ResponseEntity<FinlakeResponse<UserResponse>> getUser(String requestId, String id, String email, String mobileNumber) {
        UserResponse userResponse = userService.findUser(requestId, id, email, mobileNumber);
        return baseResponseService.ok(userResponse, requestId, ResponseCode.ALL_USER_FETCHED.getCode());
    }

    @Override
    public ResponseEntity<FinlakeResponse<Page<UserResponse>>> getUsers(String requestId, Pageable pageable) {
        Page<UserResponse> userResponses = userService.getAllUsers(pageable, requestId);
        return baseResponseService.ok(userResponses, requestId, ResponseCode.ALL_USER_FETCHED.getCode());
    }

    @Override
    public ResponseEntity<FinlakeResponse<Page<UserResponse>>> findAllUsersFiltered(String requestId, List<String> userIds, Pageable pageable) {
        Page<UserResponse> userResponses = userService.getAllUsersExceptSome(userIds, requestId, pageable);
        return baseResponseService.ok(userResponses, requestId, ResponseCode.ALL_USER_FETCHED.getCode());
    }

    @Override
    public ResponseEntity<FinlakeResponse<Page<UserResponse>>> findAllUsersWithMobileNumber(String requestId, List<String> mobileNumbers, Pageable pageable) {
        Page<UserResponse> userResponses = userService.getAllUsersWithMobileNumber(mobileNumbers, requestId, pageable);
        return baseResponseService.ok(userResponses, requestId, ResponseCode.ALL_USER_FETCHED.getCode());
    }
}
