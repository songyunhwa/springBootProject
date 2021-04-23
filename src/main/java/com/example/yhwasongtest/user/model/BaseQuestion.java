package com.example.yhwasongtest.user.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "question")
public class BaseQuestion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public String title;
    public String content;

    public BaseQuestion(String title , String content){
        this.title= title;
        this.content = content;
    }

    public BaseQuestion() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
