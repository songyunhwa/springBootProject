package com.example.yhwasongtest.user.model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserModel{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
    public String name;
    //public String password;
    public String tel;
    public UserModel(){
    }
    public UserModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

}
