package com.finlake.repository;

import com.finlake.model.FinanceRoom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FinanceRoomRepository extends JpaRepository<FinanceRoom, String> {

    @Query("SELECT c FROM FinanceRoom c JOIN c.created_by u WHERE u.id = :id")
    List<FinanceRoom> findAllByCreated_ById(@Param("id") String id, Pageable pageable);

    @Query("SELECT finance_room FROM FinanceRoom finance_room WHERE finance_room.status = :status AND finance_room.id IN :ids")
    List<FinanceRoom> findAllByIdAndStatusAndPagination(@Param("ids") List<String> ids, @Param("status") String status, Pageable pageable);

    @Query("SELECT finance_room FROM FinanceRoom finance_room WHERE finance_room.status = :status AND finance_room.id IN :ids")
    List<FinanceRoom> findAllByIdAndStatus(@Param("ids") List<String> ids, @Param("status") String status);

    @Query("SELECT finance_room FROM FinanceRoom finance_room WHERE finance_room.status = :status")
    List<FinanceRoom> findAllByStatusAndPagination(@Param("status") String status, Pageable pageable);

    @Query("SELECT finance_room FROM FinanceRoom finance_room WHERE finance_room.status = :status")
    List<FinanceRoom> findAllByStatus(@Param("status") String status);
}
