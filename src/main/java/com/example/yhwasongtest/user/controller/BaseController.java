package com.example.yhwasongtest.user.controller;

import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.user.model.BaseQuestion;
import com.example.yhwasongtest.user.model.UserModel;
import com.example.yhwasongtest.user.service.BaseService;
import com.example.yhwasongtest.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/v1")
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private final BaseService baseService;

    private final UserService userService;

    @Autowired
    public BaseController(BaseService baseService, UserService userService) {
        this.baseService = baseService;
        this.userService = userService;

    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/question")
    public BaseQuestion insertQuestion(@RequestBody BaseQuestion baseQuestion) throws Exception {

        return baseService.insertQuestion(baseQuestion);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/user")
    public UserModel insertUser(@RequestBody UserModel userModel) throws Exception {

        return userService.insertUser(userModel);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/user/{name}")
    public UserModel updateUser(@RequestParam(name = "name", required = true) String name,
                                @RequestBody UserModel userModel) throws Exception {
        return userService.updateUser(userModel);
    }

    @GetMapping(value = "/login")
    public UserModel login(@RequestParam(name = "name", required = true) String name,
                           @RequestParam(name = "password", required = true) String password,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        return userService.login(name, password, request, response);
    }

    @GetMapping("/main")
    @CrossOrigin
    public String main() {
        return "main";
    }

}
