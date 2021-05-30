package com.example.yhwasongtest.user.controller;

import com.example.yhwasongtest.user.model.BaseQuestion;
import com.example.yhwasongtest.user.model.UserModel;
import com.example.yhwasongtest.user.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/v1")
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private final BaseServiceImpl baseService;
    @Autowired
    public BaseController(BaseServiceImpl baseService){
        this.baseService = baseService;

    }
    @PostMapping(value = "/question")
    public BaseQuestion insertQuestion(@RequestBody BaseQuestion baseQuestion) throws Exception {

        return baseService.insertQuestion(baseQuestion);
    }

    @PostMapping(value = "/user")
    public UserModel insertUser(@RequestParam(name = "name",required = true) String name,
                                @RequestParam(name = "password",required = true) String password) throws Exception {

        return baseService.insertUser(name, password);
    }

    @GetMapping(value = "/login")
    public UserModel login(@RequestParam(name = "name",required = true) String name,
                           @RequestParam(name = "password",required = true) String password,
                           HttpServletRequest request,
                           HttpServletResponse response) throws  Exception {
        return baseService.login(name, password, request ,response);
    }

    @GetMapping("/main")
    @CrossOrigin
    public String main(){
        return "main";
    }

}
