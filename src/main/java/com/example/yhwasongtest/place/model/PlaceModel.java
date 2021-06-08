package com.example.yhwasongtest.place.model;

import com.example.yhwasongtest.youtube.model.YoutubeModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "place")
public class PlaceModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public String name; // 장소 이름
    public String area; // 지역
    public String url;  // 주문 페이지 (있다면)
    public String number; // 폰 번호
    private String subCategory; // CategoryModel 과 연결

    @Transient
    private List<YoutubeModel> youtube; // 유투브 모델과 연결

    public List<YoutubeModel> getYoutube() {
        return youtube;
    }

    public void setYoutube(List<YoutubeModel> youtube) {
        this.youtube = youtube;
    }

    public void setYoutube(YoutubeModel youtube) {
        if(this.youtube == null){
            this.youtube = new ArrayList<>();
        }
        if(!this.youtube.contains(youtube)){
            this.youtube.add(youtube);
        }
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
