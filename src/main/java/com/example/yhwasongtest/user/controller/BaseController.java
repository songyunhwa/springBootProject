package com.example.yhwasongtest.user.controller;

import com.example.yhwasongtest.common.ErrorMessage;
import com.example.yhwasongtest.user.dto.UserModelDto;
import com.example.yhwasongtest.user.model.BaseQuestion;
import com.example.yhwasongtest.user.model.CustomUserDetails;
import com.example.yhwasongtest.user.model.UserModel;
import com.example.yhwasongtest.user.service.BaseService;
import com.example.yhwasongtest.user.service.UserService;
import org.apache.catalina.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
//@CrossOrigin(origins = "http://localhost:8080")
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
    public ResponseEntity signup(@RequestBody  UserModelDto userModelDto){ // 회원 추가
        try {
            UserModel userModel = userService.signUp(userModelDto);
            return new ResponseEntity(userModel.getUsername(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            //new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());

            userService.logout(request, response);

            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/login")
    public ResponseEntity login(@RequestParam(name = "username", required = true) String username,
                           @RequestParam(name = "password", required = true) String password,
                                HttpServletRequest request,    HttpServletResponse response
                            ) {
        try {
            HttpSession session = request.getSession();
            Object userModel =session.getAttribute("login");
            if(userModel!=null){
                response.sendRedirect(request.getContextPath());
                return new ResponseEntity(userModel, HttpStatus.OK);
            }

            UserModel user = userService.login(username, password, request);

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", user.getUsername());
            jsonObject.put("role", user.getRole());
            jsonArray.add(jsonObject);
            return  new ResponseEntity(jsonArray.toString(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity home(UserModel model, final HttpSession session)
    {

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession httpSession = request.getSession(false);
            Object user = httpSession.getAttribute("login");
            return  new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/history")
    public ResponseEntity getLoginHistory()
    {

        try {
            int cnt = userService.getLoginHistory();
            return  new ResponseEntity<>(cnt, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
