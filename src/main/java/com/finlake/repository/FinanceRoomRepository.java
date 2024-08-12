package com.finlake.repository;

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
    @Query("SELECT finance_room FROM FinanceRoom finance_room JOIN User u WHERE finance_room.status = :status and u.id = :userId")
    Page<FinanceRoom> findAllByCreatedByIdAndStatus(@Param("userId") String userId, @Param("status") String status, Pageable pageable);

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
