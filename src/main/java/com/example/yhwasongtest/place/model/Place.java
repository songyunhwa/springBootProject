package com.example.yhwasongtest.place.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "place")
public class Place implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public String name; // 장소 이름
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

    public void setName(String placeName) {
        this.name = placeName;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public String getName() {
        return name;
    }
}
