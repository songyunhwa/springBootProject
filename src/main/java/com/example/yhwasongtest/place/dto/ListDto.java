package com.example.yhwasongtest.place.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Getter
@Setter
public class ListDto {

    @ApiParam(value = "장소 ID", required = true)
    public long placeId;

    @ApiParam(value = "내용(html 포함)", required = true)
    public String content;

    @ApiParam(value = "내용(text 만)", required = true)
    public String text;

    @ApiParam(value = "저장된 파일 아이디 리스트", required = true)
    public List<Long> fileId;
}
