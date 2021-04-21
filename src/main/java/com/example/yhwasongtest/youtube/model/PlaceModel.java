package com.example.yhwasongtest.youtube.model;

import javax.persistence.*;

public class PlaceModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    public String placeName; // 장소 이름
    public String area;      // 지역

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "youtubeModel")
    YoutubeModel youtubeModel;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }
}
