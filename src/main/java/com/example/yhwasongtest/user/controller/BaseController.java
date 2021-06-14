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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
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
    public ResponseEntity signup(@RequestBody  UserModelDto infoDto){ // 회원 추가
        try {
            userService.save(infoDto);
            return new ResponseEntity<>(infoDto.getEmail(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

        userService.logout(session, request, response);

        return "redirect:/api/v1/login";
    }

    @GetMapping(value = "/login")
    public ResponseEntity login(@RequestParam(name = "email", required = true) String email,
                           @RequestParam(name = "password", required = true) String password,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            ResponseEntity responseEntity = userService.login(email, password, request, response);
            return  responseEntity;
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/login/check")
    public ResponseEntity checkLogin(@RequestParam(name = "id", required = true) String sessionId) {
        try {
            UserModel userModel = userService.checkLogin(sessionId);
            return  new ResponseEntity<>(userModel, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/")
    public String userAccess(UserModel model, Authentication authentication) {
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        CustomUserDetails userDetail = (CustomUserDetails)authentication.getPrincipal();  //userDetail 객체를 가져옴
        return "user_access";
    }


}
