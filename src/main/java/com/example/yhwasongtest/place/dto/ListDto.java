package com.example.yhwasongtest.place.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListDto {

    public long placeId;
    public String content;
    public String text;
    public List<Long> fileId;
}
