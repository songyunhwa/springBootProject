package com.example.yhwasongtest.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

@Getter
@Setter
public class UserModelDto {
    private String username;
    private String password;
    private String authority;
    private String email;

    public UserModelDto() {

    }

    public UserModelDto(String username, String email, String password, String role){
        this.username = username;
        this.email = email;
        this.password = password;
        this.authority = role;
    }

}