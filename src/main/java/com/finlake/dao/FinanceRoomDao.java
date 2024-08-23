package com.finlake.dao;

import com.finlake.model.FinanceRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FinanceRoomDao {
    FinanceRoom save(FinanceRoom financeRoom);

    FinanceRoom getFinanceRoom(String requestId, String id, String status);

    Page<FinanceRoom> allFinanceRooms(String requestId, String status, Pageable pageable);

    Page<FinanceRoom> filterRoomsByUserId(String requestId, String status, String userId, String roomType, Pageable pageable);
}
