package com.example.yhwasongtest.youtube.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "youtube")
@Getter
@Setter
public class YoutubeModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public String publishedAt; //나온 시간
    public String channelId;    // 유투버 아이디
    public String channelTitle; // 유투버 이름
    public String videoId; // 동영상 아이디
    public String title;       //동영상 제목
    public String description;  // 동영상 설명

}
