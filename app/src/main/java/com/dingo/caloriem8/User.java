package com.dingo.caloriem8;

import java.io.Serializable;

public class User implements Serializable{
    String id;
    String email;
    String password;
    String displayName;
    String gender;
    String height;
    String weight;
    String birth_date;
    String accComplete;

    public User() {

    }

    public User(String id, String email, String password, String displayName, String gender, String height, String weight, String birth_date, String accComplete) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.birth_date = birth_date;
        this.accComplete = accComplete;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getGender() {
        return gender;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public String getAccComplete() {
        return accComplete;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public void setAccComplete(String accComplete) {
        this.accComplete = accComplete;
    }
}
