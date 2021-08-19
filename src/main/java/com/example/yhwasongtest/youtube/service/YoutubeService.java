package com.example.yhwasongtest.youtube.service;

import com.example.yhwasongtest.place.dto.PlaceDto;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.service.PlaceService;
import com.example.yhwasongtest.youtube.dto.YoutubeDto;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import com.example.yhwasongtest.youtube.repository.YoutubeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class YoutubeService {

    private YoutubeRepository youtubeRepostiory;
    private SearchYoutube searchService;
    private searchMango searchMango;
    private PlaceService placeService;

    @Autowired
    public YoutubeService(YoutubeRepository youtubeRepostiory, SearchYoutube searchService, searchMango searchMango,
                          PlaceService placeService){
        this.youtubeRepostiory = youtubeRepostiory;
        this.searchService = searchService;
        this.searchMango = searchMango;
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
