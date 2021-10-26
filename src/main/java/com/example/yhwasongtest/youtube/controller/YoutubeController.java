package com.example.yhwasongtest.youtube.controller;

import com.example.yhwasongtest.common.ErrorMessage;
import com.example.yhwasongtest.youtube.service.SearchYoutube;
import com.example.yhwasongtest.youtube.service.YoutubeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/v1")
public class YoutubeController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(YoutubeController.class);
    private final YoutubeService youtubeService;
    private final SearchYoutube searchYoutube;

    @Autowired
    public YoutubeController(YoutubeService youtubeService, SearchYoutube searchYoutube) {
        this.youtubeService = youtubeService;
        this.searchYoutube = searchYoutube;
    }

    @GetMapping(value = "/youtube")
    public ResponseEntity getSearchYoutube(@RequestParam(name = "channel", required = true) String channel,
                                             HttpServletRequest request) {
        String result = null;
        try {
            /*
            HttpSession session = request.getSession();
            if (session.getAttribute("login") == null) {
                return new ResponseEntity(ErrorMessage.NOT_LOGIN_INVALID.getMessage(), HttpStatus.BAD_REQUEST);
            }*/

            result = searchYoutube.searchYoutube(channel,null);
        } catch (Exception e) {
            return new ResponseEntity(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "/youtube")
    public void deleteYoutube(@RequestParam(name = "id", required = true) String videoId) {
        youtubeService.deleteYoutube(videoId);
    }

}
