package com.finlake.repository;

import com.finlake.model.RoomUser;
import com.finlake.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomUserRepository extends JpaRepository<RoomUser, String> {

    @Query("SELECT c FROM RoomUser c WHERE c.user.id = :id")
    List<RoomUser> findAllByUser_Id(String id);

    @Query("SELECT c FROM RoomUser c WHERE c.user.id = :id and c.status = :status")
    List<RoomUser> findAllByUser_IdAndStatusAndPaginate(String id, String status, Pageable pageable);

    @Query("SELECT c FROM RoomUser c WHERE c.user.id = :id and c.status = :status")
    List<RoomUser> findAllByUser_IdAndStatus(String id, String status);

    @Query("SELECT u FROM RoomUser ru JOIN ru.user u WHERE ru.finance_room.id = :id AND ru.status = :status")
    List<User> findAllByFinanceRoom_Id(String id, String status);

    @Query("SELECT u FROM RoomUser ru JOIN ru.user u WHERE ru.finance_room.id = :id AND ru.status = :status")
    List<User> findAllByFinanceRoom_IdAndPaginate(String id, String status, Pageable pageable);
}
