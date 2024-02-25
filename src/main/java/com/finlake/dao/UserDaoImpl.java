package com.finlake.dao;

import com.finlake.common.enums.ResponseCode;
import com.finlake.common.exception.InternalServerException;
import com.finlake.model.User;
import com.finlake.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
public class UserDaoImpl implements UserDao {

    private final UserRepository userRepository;

    public UserDaoImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (DataAccessException dae) {
            log.error("Error {} while accessing database during fetching users from database", dae);
            throw dae;
        } catch (Exception e) {
            log.error("Exception {} occur while fetching user by email", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Page<User> findAllUsers(Pageable pageable, String requestId) {
        try {
            return userRepository.findAll(pageable);
        } catch (DataAccessException dae) {
            log.error("Error {} while accessing database during fetching users from database for request id {}", dae, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        } catch (Exception e) {
            log.error("Error {} while getting users from database for request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public Page<User> findAllUsersWithMobileNumber(List<String> mobileNumbers, String requestId, Pageable pageable) {
        try {
            return userRepository.findByMobileNumberIn(mobileNumbers, pageable);
        } catch (DataAccessException dae) {
            log.error("Error {} while accessing database during fetching users with mobile numbers from database for request id {}", dae, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        } catch (Exception e) {
            log.error("Error {} while getting users from database with mobile numbers for request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public Page<User> findAllUsersExceptSome(List<String> userIds, String requestId, Pageable pageable) {
        try {
            return userRepository.findByIdNotIn(userIds, pageable);
        } catch (DataAccessException dae) {
            log.error("Error {} while accessing database during fetching users except some with ids from database for request id {}", dae, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        } catch (Exception e) {
            log.error("Error {} while getting users except some from database with ids for request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }
}
