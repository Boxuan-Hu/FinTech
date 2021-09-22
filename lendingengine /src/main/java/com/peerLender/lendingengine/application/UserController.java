package com.peerLender.lendingengine.application;

import com.peerLender.lendingengine.application.service.TokenValidateService;
import com.peerLender.lendingengine.domain.model.User;
import com.peerLender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final TokenValidateService tokenValidateService;

    @Autowired
    public UserController(UserRepository userRepository, TokenValidateService tokenValidateService) {
        this.userRepository = userRepository;
        this.tokenValidateService = tokenValidateService;
    }

    @GetMapping(value = "/users")
    public List<User> findUsers(HttpServletRequest request) {
        tokenValidateService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        return userRepository.findAll();
    }



}
