package com.example.yhwasongtest.user.model;

import lombok.Data;

@Data
public class CustomOAuth2UserResponse {
    private String accessToken;
    private String expiresIn;
    private String refreshToken;
    private String scope;
    private String tokenType;
    private String idToken;

}
