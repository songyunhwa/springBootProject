package com.example.yhwasongtest.service.repository;

import com.example.yhwasongtest.model.BaseQuestion;
import com.example.yhwasongtest.model.UserModel;

public interface BaseService {
    BaseQuestion insertQuestion(BaseQuestion baseQuestion);
    UserModel insertUser(UserModel userModel);
}
