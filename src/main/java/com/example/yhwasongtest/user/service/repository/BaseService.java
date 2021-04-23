package com.example.yhwasongtest.user.service.repository;

import com.example.yhwasongtest.user.model.BaseQuestion;
import com.example.yhwasongtest.user.model.UserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface BaseService {
    BaseQuestion insertQuestion(BaseQuestion baseQuestion);
    UserModel insertUser(String name , String password);
    UserModel login(String name, String password, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
