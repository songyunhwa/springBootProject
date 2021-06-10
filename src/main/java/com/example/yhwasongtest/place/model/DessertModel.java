package com.example.yhwasongtest.place.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "dessert")
public class DessertModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String subCategory;     // 음식 이름
    private String included;    // 포함 글자
}
