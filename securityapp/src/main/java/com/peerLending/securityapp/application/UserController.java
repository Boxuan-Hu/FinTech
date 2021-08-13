package com.peerLending.securityapp.application;

import com.peerLending.securityapp.user.exception.UserNotFoundException;
import com.peerLending.securityapp.user.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/validate")
    public String validateTokenAndUsername(@RequestHeader("Authorization") String token) {
        return userRepository.findById(token).orElseThrow(() -> new UserNotFoundException()).getUsername();
    }

}
