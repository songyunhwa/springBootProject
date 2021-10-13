package com.example.yhwasongtest.youtube.service;

import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.service.PlaceService;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import com.example.yhwasongtest.youtube.repository.YoutubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YoutubeService {

    private YoutubeRepository youtubeRepostiory;
    private SearchYoutube searchService;
    private PlaceService placeService;

    @Autowired
    public YoutubeService(YoutubeRepository youtubeRepostiory, SearchYoutube searchService,
                          PlaceService placeService){
        this.youtubeRepostiory = youtubeRepostiory;
        this.searchService = searchService;
        this.placeService = placeService;
    }

    @Autowired
    public void YoutubeController(YoutubeRepository youtubeRepository){
        this.youtubeRepostiory = youtubeRepository;

    }

    public YoutubeModel insertYoutubeModel(YoutubeModel youtubemodel){
        youtubeRepostiory.save(youtubemodel);

        return youtubemodel;
    }

    public String getSearchYoutube(String msg, String category) {
        String result = "";
        if(msg == null) {
            System.err.println("getSearchYoutube Error ==> "+ "message is not exist.");
            return "message is not exist.";
        } else {
            try {
            result = searchService.searchYoutube(msg, category, null);

            // 분류하기
            List<PlaceModel> placeModelList = placeService.getPlaceListBySubCategory("etc");
            if(placeModelList!=null){
            placeService.getDessertCategory();
            placeService.getFoodCategory();
            }
            } catch (Exception error) {
                System.err.println("getSearchYoutube Error ==> "+ error);

            }
        }
        return result;
    }

}
