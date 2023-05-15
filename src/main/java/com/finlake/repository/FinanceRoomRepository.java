package com.finlake.repository;

import com.finlake.model.FinanceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FinanceRoomRepository extends JpaRepository<FinanceRoom, String> {

    @Query("SELECT c FROM FinanceRoom c JOIN c.created_by u WHERE u.id = :id")
    List<FinanceRoom> findAllByCreated_ById(String id);
}
