package com.finlake.repository;

import com.finlake.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);

    @Query("select user from User user")
    List<User> findAllUsers(Pageable pageable);
}
