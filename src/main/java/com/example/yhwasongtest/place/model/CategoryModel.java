package com.example.yhwasongtest.place.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "place")
public class CategoryModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String mainCategory;    //  종류 ( 한식 양식 중식 디저트
    private String middleCategory;
    private String subCategory;     // 음식 이름
    private String country;         //나라

}