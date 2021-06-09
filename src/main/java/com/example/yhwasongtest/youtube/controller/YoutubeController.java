package com.example.yhwasongtest.youtube.controller;

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
    @PostMapping(value = "/youtube")
    public YoutubeModel insertYoutubeModel(@RequestBody YoutubeModel youtubeModel) throws Exception {

        return youtubeService.insertYoutubeModel(youtubeModel);
    }

    @GetMapping(value="/youtube")
    public String getSearchYoutube(HttpSession session,
                                   @RequestParam(name = "msg",required = true) String msg,
                                   @RequestParam(name = "category",required = true) String category){
        logger.info(session.getId());
        logger.info(String.valueOf(session.getAttribute("login")));
        String result = "";

            result = youtubeService.getSearchYoutube(msg, category);

        return result;

    }

    @RequestMapping(method = RequestMethod.GET, value = "/mango")
    public String getSearchMango(@RequestParam( name = "location",required = true) String location) throws Exception{

        String result = "";
        try {
            result = youtubeService.getSearchMango(location);
        } catch (Exception error) {
            System.err.println("getSearchMango Error ==> "+ error);

        }

        return result;

    }


}
