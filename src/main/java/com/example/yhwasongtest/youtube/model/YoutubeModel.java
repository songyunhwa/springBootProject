package com.example.yhwasongtest.youtube.model;

import com.example.yhwasongtest.place.model.PlaceModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "youtube")
public class YoutubeModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public String publishedAt; //나온 시간
    public String channelId;    // 유투버 아이디
    public String channelTitle; // 유투버 이름
    public String title;       //동영상 제목
    public String description;  // 동영상 설명
    public String videoId; // 동영상 id

    @ManyToOne(
            targetEntity = PlaceModel.class
            ,cascade = CascadeType.ALL
            ,fetch = FetchType.LAZY
            ,optional = false
    )
    @JoinColumn(name="place_id")
    private PlaceModel place;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PlaceModel getPlace() {
        return place;
    }

    public void setPlace(PlaceModel place) {
        this.place = place;
    }
}
