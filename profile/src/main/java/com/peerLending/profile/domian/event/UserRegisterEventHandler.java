package com.peerLending.profile.domian.event;

import com.google.gson.Gson;
import com.peerLending.profile.domian.model.User;
import com.peerLending.profile.domian.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterEventHandler {

    private Logger LOGGER = (Logger) LoggerFactory.getLogger(UserRegisterEventHandler.class);
    private static final Gson GSON = new Gson();
    private final UserRepository userRepository;

    @Autowired
    public UserRegisterEventHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void handleUserRegistration(String userDetails) {
        User user = GSON.fromJson(userDetails, User.class);
        LOGGER.info("user {} registered", user.getUsername());
        userRepository.save(user);
    }
}
