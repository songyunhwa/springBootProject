package com.example.yhwasongtest.place.dto;

public class PointDto implements Comparable<PointDto> {
    public String category;
    public Integer point;

    public PointDto(String category, Integer point) {
        this.category = category;
        this.point = point;
    }

    @Override
    public int compareTo(PointDto o) {
        if (this.point > o.point) { // 점수 내림차순
            return -1;
        } else return 1;
    }


}
