package com.example.yhwasongtest.place.model;

import com.example.yhwasongtest.youtube.model.YoutubeModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "place")
public class PlaceModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public String name; // 장소 이름

    @Column(nullable = true)
    public String area; // 지역

    @Column(nullable = true)
    public String url;  // 주문 페이지 (있다면)

    @Column(nullable = true)
    public String number; // 폰 번호

    public String subCategory; // CategoryModel 과 연결

    @Column(columnDefinition ="0")
    public int recommend; // 추천수

    @Column(columnDefinition ="0")
    public int view; // 조회수

    @Transient
    @OneToMany(mappedBy="place", fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name="place_id")
    private List<YoutubeModel> youtube = new ArrayList<>(); // 유투브 모델과 연결

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

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

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}
