package com.example.yhwasongtest.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

public class UserModelDto {
    private String email;
    private String password;

    private String authority;

    public UserModelDto(String email, String password, String role){
        this.email = email;
        this.password = password;
        this.authority = role;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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