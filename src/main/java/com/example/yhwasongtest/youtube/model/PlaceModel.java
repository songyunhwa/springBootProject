package com.example.yhwasongtest.youtube.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "place")
public class PlaceModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public String placeName; // 장소 이름
    public String area;      // 지역

    /*
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "youtubeModel")
    YoutubeModel youtubeModel;
*/
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
