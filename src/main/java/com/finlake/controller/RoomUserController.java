package com.finlake.controller;

import com.finlake.model.RoomUser;
import com.finlake.model.User;
import com.finlake.repository.RoomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("/v1")
@RequestMapping("/v1")
public class RoomUserController {

    @Autowired
    RoomUserRepository roomUserRepository;

    @PostMapping("/newRoomUser")
    public RoomUser saveRoomUser(@RequestBody RoomUser roomUser) {
        return roomUserRepository.save(roomUser);
    }

    @GetMapping("/listAllRoomUsers")
    public List<RoomUser> getRoomUsers() {
        return roomUserRepository.findAll();
    }

    @GetMapping("/filterUserFromRoomUser")
    public List<RoomUser> filterUserFromRoomUser(@RequestParam("id") String id) {
        System.out.println(id + " " + roomUserRepository.findAllByUser_Id(id).toString());
        return roomUserRepository.findAllByUser_Id(id);
    }

    @GetMapping("/filterFinanceRoomFromRoomUser")
    public List<User> filterFinanceRoomFromRoomUser(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(name = "pagination", required = false, defaultValue = "true") boolean pagination,
            @RequestParam(name = "status", required = false, defaultValue = "active") String status,
            @RequestParam(name = "id") String id
    ) {
        if (!pagination) {
            return roomUserRepository.findAllByFinanceRoom_Id(id, status);
        }
        List<Sort.Order> sorting = new ArrayList<>();
        sorting.add(new Sort.Order(Sort.Direction.ASC, "user.name"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sorting));

        return roomUserRepository.findAllByFinanceRoom_IdAndPaginate(id, status, pageable);
    }
}
