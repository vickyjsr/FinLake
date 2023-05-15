package com.finlake.controller;

import com.finlake.model.FinanceRoom;
import com.finlake.repository.FinanceRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8080")
public class FinanceRoomController {

    @Autowired
    private FinanceRoomRepository financeRoomRepository;

    @PostMapping("/newFinanceRoom")
    public FinanceRoom saveFinanceRoom(@RequestBody FinanceRoom financeRoom) {
        System.out.println(financeRoom.toString());
        return financeRoomRepository.save(financeRoom);
    }

    @GetMapping("/listAllFinanceRooms")
    List<FinanceRoom> getUsers() {
        return financeRoomRepository.findAll();
    }

    @GetMapping("/filterUserFromFinanceRoom")
    public List<FinanceRoom> filterUserFromFinanceRoom(@RequestParam("id") String id) {
        return financeRoomRepository.findAllByCreated_ById(id);
    }

}
