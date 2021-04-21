package com.example.yhwasongtest.user.service.repository;

import com.example.yhwasongtest.user.model.BaseQuestion;
import com.example.yhwasongtest.user.model.UserModel;

public interface BaseService {
    BaseQuestion insertQuestion(BaseQuestion baseQuestion);
    UserModel insertUser(UserModel userModel);
}
