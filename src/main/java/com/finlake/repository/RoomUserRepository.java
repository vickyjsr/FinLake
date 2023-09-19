package com.finlake.repository;

import com.finlake.model.RoomUser;
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

    @Query("SELECT c FROM RoomUser c JOIN c.finance_room u WHERE u.id = :id")
    List<RoomUser> findAllByFinanceRoom_Id(String id);
}
