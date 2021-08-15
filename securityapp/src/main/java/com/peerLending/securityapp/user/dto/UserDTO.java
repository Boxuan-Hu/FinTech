package com.peerLending.securityapp.user.dto;

import javax.persistence.Entity;
import javax.persistence.Id;


public class UserDTO {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int age;
    private String occupation;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getOccupation() {
        return occupation;
    }


    // for hibernate
    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
