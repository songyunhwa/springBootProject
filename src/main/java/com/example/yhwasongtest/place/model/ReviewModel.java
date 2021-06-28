package com.example.yhwasongtest.place.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "review")
public class ReviewModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public String placeName; // 장소 이름
    public long placeId; // 장소 아이디

    public String userName; // 유저 이름
    public long userId; // 유저 아이디

    public String contents; // 리뷰 내용
    public int star; // 별 개수

    public long prevId; // 대댓글이라면 리뷰 아이디


}
