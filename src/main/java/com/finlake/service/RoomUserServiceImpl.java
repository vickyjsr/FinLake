package com.finlake.service;

import com.finlake.common.enums.ResponseCode;
import com.finlake.common.exception.InternalServerException;
import com.finlake.common.mapper.RoomUserMapper;
import com.finlake.dao.RoomUserDaoImpl;
import com.finlake.model.RoomUser;
import com.finlake.model.User;
import com.finlake.model.request.RoomUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoomUserServiceImpl implements RoomUserService {

    private final RoomUserDaoImpl roomUserDao;
    private final RoomUserMapper roomUserMapper;

    public RoomUserServiceImpl(RoomUserDaoImpl roomUserDao, RoomUserMapper roomUserMapper) {
        this.roomUserDao = roomUserDao;
        this.roomUserMapper = roomUserMapper;
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
}
