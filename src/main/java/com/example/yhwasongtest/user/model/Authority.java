package com.example.yhwasongtest.user.model;


import lombok.*;

import javax.persistence.*;

@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Authority{

    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int seq;

    @Column(name = "username")
    private String username;

    @Column(name = "authority")
    private String authority;

}