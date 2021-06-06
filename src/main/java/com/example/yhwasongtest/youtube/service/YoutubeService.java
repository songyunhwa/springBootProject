package com.example.yhwasongtest.youtube.service;

import com.example.yhwasongtest.youtube.model.YoutubeModel;
import com.example.yhwasongtest.youtube.repository.YoutubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YoutubeService {

    private YoutubeRepository youtubeRepostiory;
    private SearchYoutube searchService;
    private searchMango searchMango;

    @Autowired
    public YoutubeService(YoutubeRepository youtubeRepostiory, SearchYoutube searchService, searchMango searchMango){
        this.youtubeRepostiory = youtubeRepostiory;
        this.searchService = searchService;
        this.searchMango = searchMango;
    }

    @Autowired
    public void YoutubeController(YoutubeRepository youtubeRepository){
        this.youtubeRepostiory = youtubeRepository;

    }

    public YoutubeModel insertYoutubeModel(YoutubeModel youtubemodel){
        youtubeRepostiory.save(youtubemodel);

        return youtubemodel;
    }

    public boolean getSearchYoutube(String msg) throws Exception {
        boolean result = false;
        if(msg == null) {
            throw new Exception("message is not exist.");
        } else {
            result = searchService.searchYoutube(msg);
        }
        return result;
    }

    public String getSearchMango(String location) throws  Exception {
        String result = "";
        if(location == null) {
            throw new Exception("message is not exist.");
        } else {
            result = searchMango.searchMango(location);
            System.out.println(result);
        }
        return result;
    }
}
