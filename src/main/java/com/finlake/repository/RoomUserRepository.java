package com.finlake.repository;

import com.finlake.model.RoomUser;
import com.finlake.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoomUserRepository extends JpaRepository<RoomUser, String> {

    @Transactional(readOnly = true)
    @Query("SELECT ru FROM RoomUser ru WHERE ru.userId = :userId")
    Page<RoomUser> findAllByUserId(String userId, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT ru FROM RoomUser ru WHERE ru.userId = :userId and ru.status = :status")
    Page<RoomUser> findAllByUserIdAndStatus(String userId, String status, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT u FROM RoomUser ru JOIN User u WHERE ru.financeRoomId = :financeRoomId AND ru.status = :status")
    Page<User> findAllByFinanceRoomId(String financeRoomId, String status, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT ru from RoomUser ru where ru.status = :status")
    Page<RoomUser> findAllRoomUsers(Pageable pageable);

    @Query("SELECT u FROM RoomUser ru JOIN User u on ru.userId = u.id WHERE ru.financeRoomId = :financeRoomId AND ru.status = :status")
    List<User> findAllByFinanceRoomIdAndStatus(String financeRoomId, String status);
}
