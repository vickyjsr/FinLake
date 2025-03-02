package com.finlake.dao;

import com.finlake.model.RoomUser;
import com.finlake.model.User;
import com.finlake.model.request.RoomUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoomUserDao {

    RoomUser saveRoomUser(RoomUser roomUser);

    Page<RoomUser> getRoomUsers(String requestId, Pageable pageable);

    Page<RoomUser> filterUserFromRoomUser(String requestId, Pageable pageable, String userId, String status);

    Page<User> filterFinanceRoomFromRoomUser(String requestId, Pageable pageable, String status, String financeRoomId);

    List<User> getRoomUsersWithUser(String requestId, String financeRoomId, String status);
}
