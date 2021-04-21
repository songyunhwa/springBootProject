package com.example.yhwasongtest.youtube.controller;

import com.example.yhwasongtest.user.model.BaseQuestion;
import com.example.yhwasongtest.user.service.impl.BaseServiceImpl;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import com.example.yhwasongtest.youtube.service.YoutubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/youtube")
public class YoutubeController {

    private final YoutubeService youtubeService;
    @Autowired
    public YoutubeController(YoutubeService youtubeService){
        this.youtubeService = youtubeService;

    }
    @PutMapping(value = "/")
    public YoutubeModel insertYoutubeModel(@RequestBody YoutubeModel youtubeModel) throws Exception {

        return youtubeService.insertYoutubeModel(youtubeModel);
    }
}
