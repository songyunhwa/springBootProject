package com.example.yhwasongtest.youtube.model;

import javax.persistence.*;

public class YoutubeModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    public String title;
    public String youtuberName;
    public String contents;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setYoutuberName(String youtuberName) {
        this.youtuberName = youtuberName;
    }

    public String getYoutuberName() {
        return youtuberName;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return contents;
    }
}
