package com.example.yhwasongtest.youtube.controller;

import com.example.yhwasongtest.user.model.BaseQuestion;
import com.example.yhwasongtest.user.service.impl.BaseServiceImpl;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import com.example.yhwasongtest.youtube.service.YoutubeService;
import com.google.api.services.youtube.model.SearchResult;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/api/v1")
public class YoutubeController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(YoutubeController.class);
    private final YoutubeService youtubeService;
    @Autowired
    public YoutubeController(YoutubeService youtubeService){
        this.youtubeService = youtubeService;

    }
    @PostMapping(value = "/youtube")
    public YoutubeModel insertYoutubeModel(@RequestBody YoutubeModel youtubeModel) throws Exception {

        return youtubeService.insertYoutubeModel(youtubeModel);
    }

    @GetMapping(value="/youtube")
    public boolean getSearchYoutube(HttpSession session,
                                               @RequestParam(name = "msg",required = true) String msg) throws Exception{
        logger.info(session.getId());
        logger.info(String.valueOf(session.getAttribute("login")));
        boolean result = false;
        try {
            result = youtubeService.getSearchYoutube(msg);
        } catch (Exception error) {
            System.err.println("getSearchYoutube Error ==> "+ error);

        }
        return result;

    }

    @GetMapping(value="/mango")
    public String getSearchMango(HttpSession session,
                                   @RequestParam(name = "location",required = true) String location) throws Exception{
        logger.info(session.getId());
        logger.info(String.valueOf(session.getAttribute("login")));
        String result = "";
        try {
            result = youtubeService.getSearchMango(location);
        } catch (Exception error) {
            System.err.println("getSearchMango Error ==> "+ error);

        }

        return result;

    }


}
