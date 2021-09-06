package com.example.yhwasongtest.place.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "wished")
public class WishedModel {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;


        private long userId;

        private String places;

}
