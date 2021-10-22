package com.example.yhwasongtest.youtube.controller;

import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.youtube.dto.YoutubeDto;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import com.example.yhwasongtest.youtube.service.YoutubeService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/api/v1")
public class YoutubeController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(YoutubeController.class);
    private final YoutubeService youtubeService;
    @Autowired
    public YoutubeController(YoutubeService youtubeService){
        this.youtubeService = youtubeService;

    }

    @DeleteMapping(value = "/youtube")
    public void deleteYoutube(@RequestParam(name = "id",required = true) String videoId){
        youtubeService.deleteYoutube(videoId);
    }

}
