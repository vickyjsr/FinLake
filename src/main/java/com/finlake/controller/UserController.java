package com.finlake.controller;

import com.finlake.model.User;
import com.finlake.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:8080")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/newUser")
    User newUser(@RequestBody User user) {

        User userWithEmail = userRepository.findByEmail(user.getEmail());

        if (userWithEmail != null) {
            return userWithEmail;
        }

        return userRepository.save(user);
    }

    @GetMapping("/listAllUsers")
    List<User> getUsers() {
        return userRepository.findAll();
    }
}
