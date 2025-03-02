package com.finlake.service;

import com.finlake.common.enums.ResponseCode;
import com.finlake.common.exception.InternalServerException;
import com.finlake.common.mapper.RoomUserMapper;
import com.finlake.common.mapper.UserMapperImpl;
import com.finlake.dao.RoomUserDaoImpl;
import com.finlake.model.RoomUser;
import com.finlake.model.User;
import com.finlake.model.request.RoomUserDTO;
import com.finlake.model.response.RoomUserResponse;
import com.finlake.model.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RoomUserServiceImpl implements RoomUserService {

    private final RoomUserDaoImpl roomUserDao;
    private final RoomUserMapper roomUserMapper;
    private final UserMapperImpl userMapperImpl;

    public RoomUserServiceImpl(RoomUserDaoImpl roomUserDao, RoomUserMapper roomUserMapper, UserMapperImpl userMapperImpl) {
        this.roomUserDao = roomUserDao;
        this.roomUserMapper = roomUserMapper;
        this.userMapperImpl = userMapperImpl;
    }

    @Override
    public RoomUser saveRoomUser(RoomUserDTO roomUserDTO) {
        try {
            RoomUser roomUser = roomUserMapper.mapToRoomUser(roomUserDTO);
            return roomUserDao.saveRoomUser(roomUser);
        } catch (Exception e) {
            log.error("Exception {} occurred while mapping roomUserDTO to RoomUser with request id {}", e, roomUserDTO.getRequestId());
            throw new InternalServerException(roomUserDTO.getRequestId(), ResponseCode.DATA_CONVERSION_ERROR);
        }
    }

    @Override
    public Page<RoomUser> getRoomUsers(String requestId, Pageable pageable) {
        return roomUserDao.getRoomUsers(requestId, pageable);
    }

    @Override
    public Page<RoomUser> filterUserFromRoomUser(String requestId, Pageable pageable, String userId, String status) {
        return roomUserDao.filterUserFromRoomUser(requestId, pageable, userId, status);
    }

    @Override
    public Page<User> filterFinanceRoomFromRoomUser(String requestId, Pageable pageable, String status, String financeRoomId) {
        return roomUserDao.filterFinanceRoomFromRoomUser(requestId, pageable, status, financeRoomId);
    }

    @Override
    public List<UserResponse> getRoomUsersWithUserId(String requestId, String financeRoomId, String status) {
        try {
            List<User> users = roomUserDao.getRoomUsersWithUser(requestId, financeRoomId, status);
            List<UserResponse> userResponses = new ArrayList<>();
            users.forEach(user -> {
                userResponses.add(userMapperImpl.mapToUserResponse(user));
            });
            return userResponses;
        } catch (InternalServerException e) {
            log.error("Error {} while fetching room users by financeRoomId from database for request id {}", e, requestId);
            throw e;
        } catch (Exception e) {
            log.error("Error {} while converting room users by financeRoomId from user to userResponse for request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATA_CONVERSION_ERROR);
        }
    }
}
