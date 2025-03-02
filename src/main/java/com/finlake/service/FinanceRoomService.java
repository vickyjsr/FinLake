package com.finlake.service;

import com.finlake.model.FinanceRoom;
import com.finlake.model.request.FinanceRoomRequestDTO;
import com.finlake.model.response.RoomUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FinanceRoomService {

    FinanceRoom save(FinanceRoomRequestDTO financeRoomRequestDTO);

    FinanceRoom getFinanceRoom(String requestId, String id, String status);

    Page<FinanceRoom> allFinanceRooms(String requestId, String status, Pageable pageable);

    Page<FinanceRoom> filterRoomsByUserId(String requestId, String status, String userId, String roomType, Pageable pageable);
}
