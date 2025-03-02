package com.finlake.dao;

import com.finlake.common.enums.ResponseCode;
import com.finlake.common.exception.InternalServerException;
import com.finlake.model.RoomUser;
import com.finlake.model.User;
import com.finlake.model.request.RoomUserDTO;
import com.finlake.repository.RoomUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
public class RoomUserDaoImpl implements RoomUserDao {

    private final RoomUserRepository roomUserRepository;

    public RoomUserDaoImpl(RoomUserRepository roomUserRepository) {
        this.roomUserRepository = roomUserRepository;
    }

    @Override
    @Transactional
    public RoomUser saveRoomUser(RoomUser roomUser) {
        try {
            return roomUserRepository.saveAndFlush(roomUser);
        } catch (DataAccessException dae) {
            log.error("Exception {} while saving room user record to database for request id {}", dae, roomUser.getRequestId());
            throw new InternalServerException(roomUser.getRequestId(), ResponseCode.DATABASE_ACCESS_ERROR);
        } catch (Exception e) {
            log.error("Exception {} while saving room user from database for request id {}", e, roomUser.getRequestId());
            throw new InternalServerException(roomUser.getRequestId(), ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public Page<RoomUser> getRoomUsers(String requestId, Pageable pageable) {
        try {
            return roomUserRepository.findAllRoomUsers(pageable);
        } catch (DataAccessException dae) {
            log.error("Error {} while accessing database during fetching room users from database for request id {}", dae, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        } catch (Exception e) {
            log.error("Error {} while fetching room users from database for request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public Page<RoomUser> filterUserFromRoomUser(String requestId, Pageable pageable, String userId, String status) {
        try {
            return roomUserRepository.findAllByUserIdAndStatus(userId, status, pageable);
        } catch (DataAccessException dae) {
            log.error("Error {} while accessing database during fetching room users by userId from database for request id {}", dae, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        } catch (Exception e) {
            log.error("Error {} while fetching room users by userId from database for request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public Page<User> filterFinanceRoomFromRoomUser(String requestId, Pageable pageable, String status, String financeRoomId) {
        try {
            return roomUserRepository.findAllByFinanceRoomId(financeRoomId, status, pageable);
        } catch (DataAccessException dae) {
            log.error("Error {} while accessing database during fetching room users by financeRoomId from database for request id {}", dae, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        } catch (Exception e) {
            log.error("Error {} while fetching room users by financeRoomId from database for request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    @Override
    public List<User> getRoomUsersWithUser(String requestId, String financeRoomId, String status) {
        try {
            return roomUserRepository.findAllByFinanceRoomIdAndStatus(financeRoomId, status);
        } catch (DataAccessException dae) {
            log.error("Error {} while accessing database during fetching room users by financeRoomId from database for request id {}", dae, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        } catch (Exception e) {
            log.error("Error {} while fetching room users by financeRoomId from database for request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }
}
