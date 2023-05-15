package com.finlake.controller;

import com.finlake.model.User;
import com.finlake.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("http://localhost:8080")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public User userLogin(@RequestBody User user) {
//        throw an exception if email not present and password not present
        User searchedUser = userRepository.findByEmail(user.getEmail());
        if (searchedUser != null && searchedUser.getPassword().equals(user.getPassword())) {
            return searchedUser;
        }
        // make an exception class user_not_found and return a response as json
        return null;
    }

}
