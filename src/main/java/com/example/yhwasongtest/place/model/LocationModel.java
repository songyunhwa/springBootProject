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

    public String city; //시
    public String district; // 구
    public String neighborhood; // 동

}
