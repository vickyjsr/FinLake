package com.finlake.controller;

import com.finlake.common.helper.JsonPayloadConverter;
import com.finlake.common.enums.GlobalEnum;
import com.finlake.common.enums.RoomType;
import com.finlake.model.*;
import com.finlake.model.response.UserResponse;
import com.finlake.repository.FinanceRoomRepository;
import com.finlake.repository.RoomUserRepository;
import com.finlake.repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("/v1")
@RequestMapping("/v1")
@Tag(name = "3. Finance Room Controller")
public class FinanceRoomController {

    private final FinanceRoomRepository financeRoomRepository;

    private final RoomUserRepository roomUserRepository;

    private final UserRepository userRepository;

    public FinanceRoomController(FinanceRoomRepository financeRoomRepository, RoomUserRepository roomUserRepository, UserRepository userRepository) {
        this.financeRoomRepository = financeRoomRepository;
        this.roomUserRepository = roomUserRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/newFinanceRoom")
    public FinanceRoom saveFinanceRoom(@RequestBody String financeRoomRequestData) {

        FinanceRoomBody financeRoomBody = JsonPayloadConverter.jsonToFinanceRoomBody(financeRoomRequestData);

        List<UserResponse> userResponses = JsonPayloadConverter.jsonToUserResponseList(financeRoomRequestData);

        if (financeRoomBody == null || userResponses == null) {
            return null;
        }

        FinanceRoom financeRoom = new FinanceRoom();
        financeRoom.setName(financeRoomBody.getName());
        financeRoom.setStatus(GlobalEnum.STATUS_ACTIVE.getStringValue());

        Optional<User> roomUserCreator = userRepository.findById(financeRoomBody.getCreated_by());

        if (roomUserCreator.isEmpty()) {
            return null;
        } else {
            financeRoom.setCreated_by(roomUserCreator.get());
        }

        if (financeRoomBody.getRoom_type().equals(RoomType.GROUP.getValue())) {
            financeRoom.setRoom_type(RoomType.GROUP);
        } else {
            financeRoom.setRoom_type(RoomType.ONE_ON_ONE);
        }

        FinanceRoom createFinanceRoom = financeRoomRepository.save(financeRoom);
        User user = roomUserCreator.get();
        UserResponse userCreatorResponse = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getMobileNumber(), user.getCreatedAt(), user.getUpdatedAt());
        userResponses.add(userCreatorResponse);
        createFinanceRoomWithUser(createFinanceRoom, userResponses);
        return createFinanceRoom;
    }

    @GetMapping("/get_finance_room_name")
    String getFinanceRoomName(@RequestParam(name = "id") String id) {
        Optional<FinanceRoom> financeRoomOptional = financeRoomRepository.findById(id);
        if (financeRoomOptional.isPresent()) {
            return financeRoomOptional.get().getName();
        } else {
            return "";
        }
    }

    @GetMapping("/listAllFinanceRooms")
    List<FinanceRoom> getAllFinanceRooms(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(name = "pagination", required = false, defaultValue = "true") boolean pagination,
            @RequestParam(name = "status", required = false, defaultValue = "active") String status
    ) {
        if (pagination) {
            List<Sort.Order> sorting = new ArrayList<>();
            sorting.add(new Sort.Order(Sort.Direction.ASC, "updated_at"));
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorting));
            return financeRoomRepository.findAllByStatusAndPagination(status, pageable);
        }
        return financeRoomRepository.findAllByStatus(status);
    }

    @GetMapping("/filterUserFromFinanceRoom")
    public List<FinanceRoom> filterUserFromFinanceRoom(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(name = "pagination", required = false, defaultValue = "true") boolean pagination,
            @RequestParam(name = "status", required = false, defaultValue = "active") String status,
            @RequestParam(name = "id") String user_id
    ) {
        System.out.println("Fetched" + page + " " + pageSize + " " + pagination + " " + status + " " + user_id);
        List<Sort.Order> sorting = new ArrayList<>();
        sorting.add(new Sort.Order(Sort.Direction.ASC, "updated_at"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorting));
        List<RoomUser> roomUsers;
        if (pagination) {
            roomUsers = roomUserRepository.findAllByUser_IdAndStatusAndPaginate(user_id, status, pageable);
        } else {
            roomUsers = roomUserRepository.findAllByUser_IdAndStatus(user_id, status);
        }
        List<String> financeRoomIds = new ArrayList<>();
        for (RoomUser roomUser : roomUsers) {
            financeRoomIds.add(roomUser.getFinance_room().getId());
        }
        return financeRoomRepository.findAllByIdAndStatus(financeRoomIds, status);
    }

    public void createFinanceRoomWithUser(FinanceRoom financeRoom, List<UserResponse> userList) {
        for (UserResponse userResponse : userList) {
            RoomUser roomUser = new RoomUser();
            Optional<User> user = userRepository.findById(userResponse.getId());
            user.ifPresent(roomUser::setUser);
            roomUser.setFinance_room(financeRoom);
            roomUser.setStatus(GlobalEnum.STATUS_ACTIVE.getStringValue());
            roomUserRepository.save(roomUser);
        }
    }
}
