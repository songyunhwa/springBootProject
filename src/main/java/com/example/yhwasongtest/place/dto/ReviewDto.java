package com.example.yhwasongtest.place.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    public long id;
    public String placeName;
    public long placeId;
    public String userName;
    public long userId;
    public String contents;
    public int star;
    public long prevId;
    public long fileId;
}
