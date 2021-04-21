package com.example.yhwasongtest.youtube.service;

import com.example.yhwasongtest.youtube.model.YoutubeModel;
import com.example.yhwasongtest.youtube.repository.YoutubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YoutubeService {

    private YoutubeRepository youtubeRepostiory;

    @Autowired
    public void YoutubeController(YoutubeRepository youtubeRepository){
        this.youtubeRepostiory = youtubeRepository;

    }

    public YoutubeModel insertYoutubeModel(YoutubeModel youtubemodel){
        youtubeRepostiory.save(youtubemodel);

        return youtubemodel;
    }
}
