package com.example.yhwasongtest.redis.model;

import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@RedisHash("login")
public class RedisModel implements Serializable {
    private int id;
    private String username;
    private String email;
    private String role;        // 권한
}
