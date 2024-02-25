package com.finlake.controller;

import com.finlake.common.enums.ResponseCode;
import com.finlake.model.RoomUser;
import com.finlake.model.User;
import com.finlake.model.request.RoomUserDTO;
import com.finlake.model.response.FinlakeResponse;
import com.finlake.service.BaseResponseService;
import com.finlake.service.RoomUserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("/v1")
@RequestMapping("/v1/roomUser")
@Tag(name = "4. Room User Controller")
public class RoomUserController implements RoomUserControllerApi {

    private final RoomUserServiceImpl roomUserService;
    private final BaseResponseService baseResponseService;

    public RoomUserController(RoomUserServiceImpl roomUserService, BaseResponseService baseResponseService) {
        this.roomUserService = roomUserService;
        this.baseResponseService = baseResponseService;
    }

    @Override
    public ResponseEntity<FinlakeResponse<RoomUser>> saveRoomUser(RoomUserDTO roomUserDTO) {
        RoomUser roomUser = roomUserService.saveRoomUser(roomUserDTO);
        return baseResponseService.ok(roomUser, roomUserDTO.getRequestId(), ResponseCode.ROOM_USER_CREATED.getCode());
    }

    @Override
    public ResponseEntity<FinlakeResponse<Page<RoomUser>>> getRoomUsers(String requestId, Pageable pageable) {
        Page<RoomUser> roomUsers = roomUserService.getRoomUsers(requestId, pageable);
        return baseResponseService.ok(roomUsers, requestId, ResponseCode.ROOM_USER_FETCHED.getCode());
    }

    @Override
    public ResponseEntity<FinlakeResponse<Page<RoomUser>>> filterUserFromRoomUser(String requestId, Pageable pageable, String userId, String status) {
        Page<RoomUser> roomUsers = roomUserService.filterUserFromRoomUser(requestId, pageable, userId, status);
        return baseResponseService.ok(roomUsers, requestId, ResponseCode.ROOM_USER_FETCHED.getCode());
    }

    @Override
    public ResponseEntity<FinlakeResponse<Page<User>>> filterFinanceRoomFromRoomUser(String requestId, Pageable pageable, String status, String financeRoomId) {
        Page<User> users = roomUserService.filterFinanceRoomFromRoomUser(requestId, pageable, status, financeRoomId);
        return baseResponseService.ok(users, requestId, ResponseCode.ROOM_USER_FETCHED.getCode());
    }
}
