package com.finlake.dao;

import com.finlake.common.enums.ResponseCode;
import com.finlake.common.enums.RoomType;
import com.finlake.common.exception.InternalServerException;
import com.finlake.model.FinanceRoom;
import com.finlake.repository.FinanceRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class FinanceRoomDaoImpl implements FinanceRoomDao {

    private final FinanceRoomRepository financeRoomRepository;

    public FinanceRoomDaoImpl(FinanceRoomRepository financeRoomRepository) {
        this.financeRoomRepository = financeRoomRepository;
    }

    @Override
    @Transactional
    @Modifying
    public FinanceRoom save(FinanceRoom financeRoom) {
        try {
            return financeRoomRepository.saveAndFlush(financeRoom);
        } catch (DataAccessException dae) {
            log.error("Exception {} while saving finance room record to database for request id {}", dae, financeRoom.getRequestId());
            throw new InternalServerException(financeRoom.getRequestId(), ResponseCode.DATABASE_ACCESS_ERROR);
        } catch (Exception e) {
            log.error("Exception {} while saving finance room from database for request id {}", e, financeRoom.getRequestId());
            throw new InternalServerException(financeRoom.getRequestId(), ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public FinanceRoom getFinanceRoom(String requestId, String id, String status) {
        try {
            return financeRoomRepository.findByIdAndStatus(id, status);
        } catch (DataAccessException dae) {
            log.error("Error {} while accessing database during fetching finance room from database for request id {}", dae, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        } catch (Exception e) {
            log.error("Error {} while fetching finance room from database for request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public Page<FinanceRoom> allFinanceRooms(String requestId, String status, Pageable pageable) {
        try {
            return financeRoomRepository.findAllByStatusAndPagination(status, pageable);
        } catch (DataAccessException dae) {
            log.error("Error {} while accessing database during fetching all finance rooms from database for request id {}", dae, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        } catch (Exception e) {
            log.error("Error {} while fetching all finance rooms from database for request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }

    @Override
    @Transactional
    public Page<FinanceRoom> filterRoomsByUserId(String requestId, String status, String userId, String roomType, Pageable pageable) {
        try {
            return financeRoomRepository.findAllByRoomUserIdAndStatus(userId, RoomType.getType(roomType), status, pageable);
        } catch (DataAccessException dae) {
            log.error("Error {} while accessing database during fetching all finance rooms by user id and status from database for request id {}", dae, requestId);
            throw new InternalServerException(requestId, ResponseCode.DATABASE_ACCESS_ERROR);
        } catch (Exception e) {
            log.error("Error {} while fetching all finance rooms by user id and status from database for request id {}", e, requestId);
            throw new InternalServerException(requestId, ResponseCode.INTERNAL_SERVER_EXCEPTION);
        }
    }
}
