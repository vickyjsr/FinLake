package com.finlake.controller;

import com.finlake.model.RoomUser;
import com.finlake.model.User;
import com.finlake.model.request.RoomUserDTO;
import com.finlake.model.response.FinlakeResponse;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public interface RoomUserControllerApi {

    @PostMapping("/new")
    ResponseEntity<FinlakeResponse<RoomUser>> saveRoomUser(@RequestBody RoomUserDTO roomUser);

    @GetMapping("/list")
    ResponseEntity<FinlakeResponse<Page<RoomUser>>> getRoomUsers(@RequestHeader @Valid @Size(min = 1, max = 50) String requestId,
                                                                 @PageableDefault(page = 0, size = 10, sort = "createdAt",
                                                                         direction = Sort.Direction.ASC) Pageable pageable);

    @GetMapping("/filterUserFromRoomUser")
    ResponseEntity<FinlakeResponse<Page<RoomUser>>> filterUserFromRoomUser(@RequestHeader @Valid @Size(min = 1, max = 50) String requestId,
                                                                           @PageableDefault(page = 0, size = 10, sort = "createdAt",
                                                                                   direction = Sort.Direction.ASC) Pageable pageable,
                                                                           @RequestParam("userId") String userId,
                                                                           @RequestParam(name = "status", required = false,
                                                                                   defaultValue = "active") String status);

    @GetMapping("/filterFinanceRoomFromRoomUser")
    ResponseEntity<FinlakeResponse<Page<User>>> filterFinanceRoomFromRoomUser(
            @RequestHeader @Valid @Size(min = 1, max = 50) String requestId,
            @PageableDefault(page = 0, size = 10, sort = "createdAt",
                    direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(name = "status", required = false, defaultValue = "active") String status,
            @RequestParam(name = "financeRoomId") String financeRoomId);
}
