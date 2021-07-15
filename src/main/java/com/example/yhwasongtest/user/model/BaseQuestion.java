package com.example.yhwasongtest.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "question")
public class BaseQuestion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public String title;
    public String content;

    public BaseQuestion() {

    }

}
