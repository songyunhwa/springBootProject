package com.example.yhwasongtest.place.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name="picture")
@NoArgsConstructor
public class PictureModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private long placeId; // 연결된 place 아이디
    private long listId; //연결된 일기장 아이디
    private long reviewId; // 연결된 review 아이디

    private String originFileName;

    private String fileName;

    private String filePath;

    private long fileSize;

}
