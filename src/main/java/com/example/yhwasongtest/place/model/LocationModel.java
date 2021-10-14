package com.example.yhwasongtest.place.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@Entity
@Table(name = "location")
public class LocationModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name; // 장소 이름
    private String address;

    private String lat; // x좌표
    private String lng; // y좌표

    private Long placeId; // placeModel id

}
