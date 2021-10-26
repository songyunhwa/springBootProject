package com.example.yhwasongtest.youtube.service;

import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.service.PlaceService;
import com.example.yhwasongtest.youtube.dto.YoutubeDto;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import com.example.yhwasongtest.youtube.repository.YoutubeRepository;
import com.google.api.services.youtube.YouTube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YoutubeService {
    private YoutubeRepository youtubeRepostiory;

    @Autowired
    public YoutubeService(YoutubeRepository youtubeRepostiory){
        this.youtubeRepostiory = youtubeRepostiory;
    }

    @Autowired
    public void YoutubeController(YoutubeRepository youtubeRepository){
        this.youtubeRepostiory = youtubeRepository;

    }

    public YoutubeModel insertYoutubeModel(YoutubeDto youtubeDto, PlaceModel placeModel){

        YoutubeModel youtubeModel = youtubeRepostiory.findByVideoId(youtubeDto.getVideoId());
        // 포함하고 있는 유투브라면 넘어가기
        if (youtubeModel == null) {
            YoutubeModel youtube = new YoutubeModel();
            youtube.setTitle(youtubeDto.getTitle());
            youtube.setChannelTitle(youtubeDto.getChannelTitle());
            youtube.setVideoId(youtubeDto.getVideoId());
            youtube.setPlace(placeModel);
            youtubeRepostiory.save(youtube);
            return youtube;
        }

        return youtubeModel;
    }

    public YoutubeModel getYoutubeModel(String videoId){
        YoutubeModel youTubeModel = youtubeRepostiory.findByVideoId(videoId);
        return youTubeModel;
    }

    public List<YoutubeModel> getYoutubeModelByPlaceId(long placeId) {
        List<YoutubeModel> youtubeModels = youtubeRepostiory.findByPlaceId(placeId);
        return youtubeModels;
    }

    public void deleteYoutube(String videoId) {
        YoutubeModel youtubeModel = youtubeRepostiory.findByVideoId(videoId);
        if(youtubeModel != null) {
            youtubeRepostiory.delete(youtubeModel);
        }
    }
}
