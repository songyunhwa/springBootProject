package com.example.yhwasongtest.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomOAuth2User {


    private String authuser;
    private String code;
    private String prompt;
    private String scope;
    private String state;



}
