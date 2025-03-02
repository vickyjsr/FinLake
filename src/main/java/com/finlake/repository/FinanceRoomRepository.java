package com.finlake.repository;

import com.finlake.common.enums.RoomType;
import com.finlake.model.FinanceRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FinanceRoomRepository extends JpaRepository<FinanceRoom, String> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT fr FROM FinanceRoom fr JOIN RoomUser ru on ru.financeRoomId = fr.id " + "WHERE fr.roomType = :roomType and fr.status = :status and ru.userId = :userId")
    Page<FinanceRoom> findAllByRoomUserIdAndStatus(@Param("userId") String userId, @Param("roomType") RoomType roomType, @Param("status") String status, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT finance_room FROM FinanceRoom finance_room WHERE finance_room.status = :status AND finance_room.id IN :ids")
    Page<FinanceRoom> findAllByIdAndStatusAndPagination(@Param("ids") List<String> ids, @Param("status") String status, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT finance_room FROM FinanceRoom finance_room WHERE finance_room.status = :status AND finance_room.id IN :ids")
    Page<FinanceRoom> findAllByIdAndStatus(@Param("ids") List<String> ids, @Param("status") String status, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT finance_room FROM FinanceRoom finance_room WHERE finance_room.status = :status")
    Page<FinanceRoom> findAllByStatusAndPagination(@Param("status") String status, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT finance_room FROM FinanceRoom finance_room WHERE finance_room.status = :status")
    Page<FinanceRoom> findAllByStatus(@Param("status") String status, Pageable pageable);

    @Transactional(readOnly = true)
    FinanceRoom findByIdAndStatus(String id, String status);
}
