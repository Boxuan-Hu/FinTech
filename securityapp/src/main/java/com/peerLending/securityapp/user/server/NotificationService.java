package com.peerLending.securityapp.user.server;

import com.google.gson.Gson;
import com.peerLending.securityapp.user.dto.UserDTO;
import com.peerLending.securityapp.user.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    private final RabbitTemplate rabbitTemplate;
    private final static Gson GSON = new Gson();

    @Autowired
    public NotificationService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sentMessage(UserDTO userDTO) {
        userDTO.setPassword(null);
        rabbitTemplate.convertAndSend("userRegisteredTopic",
                "user.registered", GSON.toJson(userDTO));
    }



}
