package com.finlake.repository;

import com.finlake.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Transactional(readOnly = true)
    User findByEmail(String email);

    @Transactional(readOnly = true)
    Page<User> findAll(Pageable pageable);

    @Transactional(readOnly = true)
    @Query("select user from User user where user.id not in :userIds")
    Page<User> findByIdNotIn(List<String> userIds, Pageable pageable);

    @Transactional(readOnly = true)
    Page<User> findByMobileNumberIn(List<String> mobileNumber, Pageable pageable);
}
