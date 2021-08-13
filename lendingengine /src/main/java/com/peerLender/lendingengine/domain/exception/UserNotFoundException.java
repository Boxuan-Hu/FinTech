package com.peerLender.lendingengine.domain.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String username) {
        super("User with id: " + username + " not found");
    }
}
