package com.finlake.controller;

import com.finlake.model.RoomUser;
import com.finlake.repository.RoomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8080")
public class RoomUserController {

    @Autowired
    private RoomUserRepository roomUserRepository;

    @PostMapping("/newRoomUser")
    public RoomUser saveRoomUser(@RequestBody RoomUser roomUser) {
        return roomUserRepository.save(roomUser);
    }

    @GetMapping("/listAllRoomUsers")
    List<RoomUser> getRoomUsers() {
        return roomUserRepository.findAll();
    }

    @GetMapping("/filterUserFromRoomUser")
    public List<RoomUser> filterUserFromRoomUser(@RequestParam("id") String id) {
        return roomUserRepository.findAllByUser_Id(id);
    }

    @GetMapping("/filterFinanceRoomFromRoomUser")
    public List<RoomUser> filterFinanceRoomFromRoomUser(@RequestParam("id") String id) {
        return roomUserRepository.findAllByFinanceRoom_Id(id);
    }

}
