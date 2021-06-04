package com.example.yhwasongtest.place.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

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

}
