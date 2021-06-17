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

    private String name; // 장소 이름

    private String area; // 지역

    private String url;  // 주문 페이지 (있다면)

    private String number; // 폰 번호

    private String subCategory; // CategoryModel 과 연결

    @Column(columnDefinition ="0")
    private int recommend; // 추천수

    @Column(columnDefinition ="0")
    private int view; // 조회수

    @OneToMany(targetEntity = YoutubeModel.class,
                cascade = CascadeType.ALL,
                fetch = FetchType.EAGER,
                mappedBy = "place")
    private List<YoutubeModel> youtubes = new ArrayList<>(); // 유투브 모델과 연결

    private Long fileId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public List<YoutubeModel> getYoutubes() {
        return youtubes;
    }

    public void setYoutubes(List<YoutubeModel> youtubes) {
        this.youtubes = youtubes;
    }

    public void setYoutube(YoutubeModel youtube) {
        this.youtubes.add(youtube);
    }
}
