package com.example.yhwasongtest.youtube.repository;

import com.example.yhwasongtest.youtube.model.YoutubeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YoutubeRepository  extends JpaRepository<YoutubeModel, Long> {
    YoutubeModel findByVideoId(String videoId);
    List<YoutubeModel> findByPlaceId(long placeId);
}
