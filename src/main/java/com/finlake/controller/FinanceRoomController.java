package com.finlake.controller;

import com.finlake.JsonPayloadConverter;
import com.finlake.enums.RoomType;
import com.finlake.model.*;
import com.finlake.models.FinanceRoomRequestData;
import com.finlake.repository.FinanceRoomRepository;
import com.finlake.repository.RoomUserRepository;
import com.finlake.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        Optional<User> user = userRepository.findById(financeRoomBody.getCreated_by());

        if (user.isEmpty()) {
        } else {
            financeRoom.setCreated_by(user.get());
        }

        if (financeRoomBody.getRoom_type().equals(RoomType.GROUP.getValue())) {
            financeRoom.setRoom_type(RoomType.GROUP);
        } else {
            financeRoom.setRoom_type(RoomType.ONE_ON_ONE);
        }

        FinanceRoom createFinanceRoom = financeRoomRepository.save(financeRoom);
        createFinanceRoomWithUser(createFinanceRoom, userResponses);
        System.out.println(createFinanceRoom.toString());
        return createFinanceRoom;
    }

    @GetMapping("/listAllFinanceRooms")
    List<FinanceRoom> getUsers() {
        return financeRoomRepository.findAll();
    }

    @GetMapping("/filterUserFromFinanceRoom")
    public List<FinanceRoom> filterUserFromFinanceRoom(@RequestParam("id") String id) {
        return financeRoomRepository.findAllByCreated_ById(id);
    }

    public void createFinanceRoomWithUser(FinanceRoom financeRoom, List<UserResponse> userList) {
        for (UserResponse userResponse : userList) {
            RoomUser roomUser = new RoomUser();
            Optional<User> user = userRepository.findById(userResponse.getId());
            user.ifPresent(roomUser::setUser);
            roomUser.setFinance_room(financeRoom);
            roomUser.setBalance("0");
            roomUserRepository.save(roomUser);
        }
    }
}
