package com.finlake.controller;

import com.finlake.model.response.FinlakeResponse;
import com.finlake.model.response.UserResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface UserControllerApi {
    @GetMapping("/v1/user/get")
    ResponseEntity<FinlakeResponse<UserResponse>> getUser(@RequestHeader @Valid @Size(min = 1, max = 50) String requestId,
                                                          @PageableDefault(page = 0, size = 10, sort = "createdAt",
                                                                  direction = Sort.Direction.ASC) Pageable pageable,
                                                          @Valid @RequestParam(value = "id") String id);

    @GetMapping("/v1/user/list")
    ResponseEntity<FinlakeResponse<Page<UserResponse>>> getUsers(@RequestHeader @Valid @Size(min = 1, max = 50) String requestId,
                                                                 @PageableDefault(page = 0, size = 10, sort = "createdAt",
                                                                         direction = Sort.Direction.ASC) Pageable pageable);

    @GetMapping("/v1/user/list/except")
    ResponseEntity<FinlakeResponse<Page<UserResponse>>> findAllUsersFiltered(@RequestHeader @Valid @Size(min = 1, max = 50) String requestId,
                                                                             @RequestParam(value = "userIds") @Valid
                                                                             @NotBlank(message = "user ids cannot be blank")
                                                                             @NotNull(message = "user ids cannot be null")
                                                                             @NotEmpty(message = "user ids cannot be empty") List<String> userIds,
                                                                             @PageableDefault(page = 0, size = 10, sort = "createdAt",
                                                                                     direction = Sort.Direction.ASC) Pageable pageable);

    @GetMapping("/v1/user/list/mobile")
    ResponseEntity<FinlakeResponse<Page<UserResponse>>> findAllUsersWithMobileNumber(@RequestHeader @Valid @Size(min = 1, max = 50) String requestId,
                                                                                     @RequestParam(value = "mobileNumbers") @Valid
                                                                                     @NotBlank(message = "mobile numbers cannot be blank")
                                                                                     @NotNull(message = "mobile numbers cannot be null")
                                                                                     @NotEmpty(message = "mobile numbers cannot be empty") List<String> mobileNumbers,
                                                                                     @PageableDefault(page = 0, size = 10, sort = "createdAt",
                                                                                             direction = Sort.Direction.ASC) Pageable pageable);
}
