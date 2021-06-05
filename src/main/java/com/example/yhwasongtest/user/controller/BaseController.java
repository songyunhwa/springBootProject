package com.example.yhwasongtest.user.controller;

import com.example.yhwasongtest.user.dto.UserModelDto;
import com.example.yhwasongtest.user.model.BaseQuestion;
import com.example.yhwasongtest.user.model.CustomUserDetails;
import com.example.yhwasongtest.user.model.UserModel;
import com.example.yhwasongtest.user.service.BaseService;
import com.example.yhwasongtest.user.service.UserService;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/v1")
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    private final BaseService baseService;

    private final UserService userService;

    @Autowired
    public BaseController(BaseService baseService,  UserService userService) {
        this.baseService = baseService;
        this.userService = userService;

    }
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public String signup(@RequestBody  UserModelDto infoDto) throws Exception{ // 회원 추가
        userService.save(infoDto);
        return infoDto.getEmail();

    }
    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/api/v1/login";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/login")
    public UserModel login(@RequestParam(name = "email", required = true) String email,
                           @RequestParam(name = "password", required = true) String password,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception {
        return userService.login(email, password, request, response);
    }

    @GetMapping("/")
    public String userAccess(UserModel model, Authentication authentication) {
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        CustomUserDetails userDetail = (CustomUserDetails)authentication.getPrincipal();  //userDetail 객체를 가져옴
        return "user_access";
    }

}
