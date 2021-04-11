package com.example.yhwasongtest.controller;

import com.example.yhwasongtest.model.BaseQuestion;
import com.example.yhwasongtest.model.UserModel;
import com.example.yhwasongtest.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class BaseController {

    private final BaseServiceImpl baseService;
    @Autowired
    public BaseController(BaseServiceImpl baseService){
        this.baseService = baseService;

    }
    @PutMapping(value = "/question")
    public BaseQuestion insertQuestion(@RequestBody BaseQuestion baseQuestion) throws Exception {

        return baseService.insertQuestion(baseQuestion);
    }

    @PutMapping(value = "/user")
    public UserModel insertUser(@RequestBody UserModel userModel) throws Exception {

        return baseService.insertUser(userModel);
    }

    @GetMapping("/api/main")
    public String main(){
        return "main";
    }

}
