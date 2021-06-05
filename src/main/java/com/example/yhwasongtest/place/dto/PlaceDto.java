package com.example.yhwasongtest.place.dto;

public class PlaceDto {

    public String name; // 장소 이름
    public String area; // 지역
    public String url;  // 주문 페이지 (있다면)
    public String number; // 폰 번호
    private String subCategory; // CategoryModel 과 연결

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
