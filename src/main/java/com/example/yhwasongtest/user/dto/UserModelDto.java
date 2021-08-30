package com.example.yhwasongtest.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

public class UserModelDto {
    private String username;
    private String password;

    private String authority;

    public UserModelDto(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.authority = role;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return authority;
    }

    public void setRole(String role) {
        this.authority = role;
    }
}