package com.finlake.service;

import com.finlake.common.enums.GlobalEnum;
import com.finlake.common.enums.ResponseCode;
import com.finlake.common.exception.DataConversionError;
import com.finlake.common.exception.InternalServerException;
import com.finlake.common.exception.UserNotFoundException;
import com.finlake.common.mapper.FinanceRoomMapper;
import com.finlake.dao.FinanceRoomDaoImpl;
import com.finlake.dao.UserDaoImpl;
import com.finlake.model.FinanceRoom;
import com.finlake.model.User;
import com.finlake.model.request.FinanceRoomRequestDTO;
import com.finlake.model.request.RoomUserDTO;
import com.finlake.model.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class FinanceRoomServiceImpl implements FinanceRoomService {

    private final FinanceRoomDaoImpl financeRoomDao;
    private final FinanceRoomMapper financeRoomMapper;
    private final RoomUserServiceImpl roomUserService;
    private final UserDaoImpl userDao;

    public FinanceRoomServiceImpl(FinanceRoomMapper financeRoomMapper, FinanceRoomDaoImpl financeRoomDao, RoomUserServiceImpl roomUserService, UserDaoImpl userDao) {
        this.financeRoomDao = financeRoomDao;
        this.financeRoomMapper = financeRoomMapper;
        this.roomUserService = roomUserService;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public FinanceRoom save(FinanceRoomRequestDTO financeRoomRequestDTO) {
        FinanceRoom newFinanceRoom = null;
        User user = userDao.findUserById(financeRoomRequestDTO.getCreatedBy());
        if (user == null) {
            throw new UserNotFoundException(financeRoomRequestDTO.getRequestId(), ResponseCode.USER_DOES_NOT_EXIST);
        }
        try {
            FinanceRoom financeRoom = financeRoomMapper.mapToFinanceRoom(financeRoomRequestDTO);
            newFinanceRoom = financeRoomDao.save(financeRoom);
        } catch (Exception e) {
            log.error("Exception {} occurred while mapping users to user responses with request id {}", e, financeRoomRequestDTO.getRequestId());
            throw new DataConversionError(financeRoomRequestDTO.getRequestId(), ResponseCode.DATA_CONVERSION_ERROR);
        }
        try {
            List<String> userIds = financeRoomRequestDTO.getUserIds();
//            todo put a check here also if user is present in db or not
            userIds.add(financeRoomRequestDTO.getCreatedBy());
            FinanceRoom finalNewFinanceRoom = newFinanceRoom;
            userIds.forEach(userId -> {
                RoomUserDTO roomUserDTO = RoomUserDTO.builder().requestId(financeRoomRequestDTO.getRequestId()).financeRoomId(finalNewFinanceRoom.getId()).userId(userId).status(GlobalEnum.STATUS_ACTIVE.getStringValue()).build();
                roomUserService.saveRoomUser(roomUserDTO);
            });
        } catch (Exception e) {
            throw new InternalServerException(financeRoomRequestDTO.getRequestId(), ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
        return newFinanceRoom;
    }

    @Override
    public FinanceRoom getFinanceRoom(String requestId, String id, String status) {
        return financeRoomDao.getFinanceRoom(requestId, id, status);
    }

    @Override
    public Page<FinanceRoom> allFinanceRooms(String requestId, String status, Pageable pageable) {
        return financeRoomDao.allFinanceRooms(requestId, status, pageable);
    }

    @Override
    public Page<FinanceRoom> filterRoomsByUserId(String requestId, String status, String userId, String roomType, Pageable pageable) {
        return financeRoomDao.filterRoomsByUserId(requestId, status, userId, roomType, pageable);
    }
}
