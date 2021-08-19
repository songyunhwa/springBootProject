package com.example.yhwasongtest.place.dto;

import com.example.yhwasongtest.youtube.dto.YoutubeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceDto {

    public String name; // 장소 이름
    public String area; // 지역
    public String url;  // 유투브 페이지
    public String number; // 폰 번호
    public String subCategory; // CategoryModel 과 연결
    public Long fileId; // 파일 아이디
    public List<YoutubeDto> youtubes;
}

