package com.example.yhwasongtest.recommend.controller;

import com.example.yhwasongtest.recommend.service.RecommendService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/v1")
public class RecommendController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RecommendController.class);
    private final RecommendService recommendService;

    @Autowired
    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    public void getRecommend(@CookieValue(name = "login", defaultValue = "null") String cookie,
                             @RequestParam(name = "id") long id,
                             HttpServletResponse response){
        // 쿠키에 아이디가 없다면 추가해주기.
        if(!cookie.contains(String.valueOf(id))){
            cookie += id + "/";
        }
        response.addCookie(new Cookie("loginCookie", cookie));

    }
}
