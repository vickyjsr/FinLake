package com.finlake.controller;

import com.finlake.model.User;
import com.finlake.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("/v1")
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/listAllUsers")
    public List<User> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "15") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("name"));
        return userRepository.findAllUsers(pageable);
    }

    @GetMapping("/listAllUsersFiltered")
    public List<User> findAllUsersFiltered(@RequestParam(defaultValue = "0") int page, String id, @RequestParam(defaultValue = "15") int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("name"));
        return userRepository.findAllUsersFiltered(id, pageable);
    }
}
