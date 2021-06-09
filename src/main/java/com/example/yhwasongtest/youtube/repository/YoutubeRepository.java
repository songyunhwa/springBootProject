package com.example.yhwasongtest.youtube.repository;

import com.example.yhwasongtest.youtube.model.YoutubeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeRepository  extends JpaRepository<YoutubeModel, Long> {
    YoutubeModel findByVideoId(String videoId);
}
