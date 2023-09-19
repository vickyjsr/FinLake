package com.finlake.controller;

import com.finlake.JsonPayloadConverter;
import com.finlake.enums.RoomType;
import com.finlake.model.*;
import com.finlake.models.FinanceRoomRequestData;
import com.finlake.repository.FinanceRoomRepository;
import com.finlake.repository.RoomUserRepository;
import com.finlake.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FinanceRoomController {

    @Autowired
    private FinanceRoomRepository financeRoomRepository;

    @Autowired
    private RoomUserRepository roomUserRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/newFinanceRoom")
    public FinanceRoom saveFinanceRoom(@RequestBody String financeRoomRequestData) {

        FinanceRoomBody financeRoomBody = JsonPayloadConverter.jsonToFinanceRoomBody(financeRoomRequestData);

        List<UserResponse> userResponses = JsonPayloadConverter.jsonToUserResponseList(financeRoomRequestData);

        if (financeRoomBody == null || userResponses == null) {
            return null;
        }

        FinanceRoom financeRoom = new FinanceRoom();
        financeRoom.setName(financeRoomBody.getName());

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
        UserResponse userCreatorResponse = new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getMobileNumber(), user.getRoleType().getStringValue(), user.getCreated_at().toString(), user.getUpdated_at().toString());
        userResponses.add(userCreatorResponse);
        createFinanceRoomWithUser(createFinanceRoom, userResponses);
        return createFinanceRoom;
    }

    @GetMapping("/listAllFinanceRooms")
    List<FinanceRoom> getAllFinanceRooms(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "10") int pageSize, @RequestParam(required = false, defaultValue = "true") boolean pagination, @RequestParam(required = false, defaultValue = "active") String status) {

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
        List<RoomUser> roomUsers = roomUserRepository.findAllByUser_Id(user_id);
        List<String> financeRoomIds = new ArrayList<>();
        for (RoomUser roomUser : roomUsers) {
            financeRoomIds.add(roomUser.getFinance_room().getId());
        }
        if (pagination) {
            List<Sort.Order> sorting = new ArrayList<>();
            sorting.add(new Sort.Order(Sort.Direction.ASC, "updated_at"));
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorting));
            System.out.println("jnd jalwn ljakwnmekal a kwlankn jfksnwbihjkciajksnfbvhcjnas");
            return financeRoomRepository.findAllByIdAndStatusAndPagination(financeRoomIds, status, pageable);
        }

        return financeRoomRepository.findAllByIdAndStatus(financeRoomIds, status);
    }

    public void createFinanceRoomWithUser(FinanceRoom financeRoom, List<UserResponse> userList) {
        for (UserResponse userResponse : userList) {
            RoomUser roomUser = new RoomUser();
            Optional<User> user = userRepository.findById(userResponse.getId());
            user.ifPresent(roomUser::setUser);
            roomUser.setFinance_room(financeRoom);
            roomUserRepository.save(roomUser);
        }
    }
}
