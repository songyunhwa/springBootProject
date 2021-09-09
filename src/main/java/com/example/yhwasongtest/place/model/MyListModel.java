package com.example.yhwasongtest.place.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@Entity
@Table(name = "my_list")
@NoArgsConstructor
public class MyListModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long userId; // 유저 아이디

    private long placeId; // 장소 아이디

    private String content;

    private long fileId;
    private String fileName;
}
