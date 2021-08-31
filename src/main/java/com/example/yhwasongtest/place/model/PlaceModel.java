package com.example.yhwasongtest.place.model;

import com.example.yhwasongtest.youtube.model.YoutubeModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    @Column(columnDefinition = "0")
    private int recommend; // 추천수

    @Column(columnDefinition = "0")
    private int view; // 조회수

    @OneToMany(targetEntity = YoutubeModel.class,
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER,
            mappedBy = "place")
    private List<YoutubeModel> youtubes = new ArrayList<>(); // 유투브 모델과 연결

    private Long fileId;


}
