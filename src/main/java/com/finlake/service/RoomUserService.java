package com.finlake.service;

import com.finlake.model.RoomUser;
import com.finlake.model.User;
import com.finlake.model.request.RoomUserDTO;
import com.finlake.model.response.RoomUserResponse;
import com.finlake.model.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomUserService {

    RoomUser saveRoomUser(RoomUserDTO roomUserDTO);

    Page<RoomUser> getRoomUsers(String requestId, Pageable pageable);

    Page<RoomUser> filterUserFromRoomUser(String requestId, Pageable pageable, String userId, String status);

    Page<User> filterFinanceRoomFromRoomUser(String requestId, Pageable pageable, String status, String financeRoomId);

    List<UserResponse> getRoomUsersWithUserId(String requestId, String financeRoomId, String status);
}
