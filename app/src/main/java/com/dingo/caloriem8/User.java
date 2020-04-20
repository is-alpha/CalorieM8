package com.dingo.caloriem8;

import java.io.Serializable;

public class User implements Serializable{
    String userId;
    String userEmail;
    String userPassword;
    String userDisplayname;
    String userGender;
    String userHeight;
    String userWeight;
    String userBirthdate;
    Boolean accComplete;

    public User() {

    }

    public User(String userId, String userEmail, String userPassword, String userDisplayname, String userGender, String userHeight, String userWeight, String userBirthdate) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userDisplayname = userDisplayname;
        this.userGender = userGender;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.userBirthdate = userBirthdate;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserDisplayname() {
        return userDisplayname;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public String getUserWeight() {
        return userWeight;
    }

    public String getUserBirthdate() {
        return userBirthdate;
    }

    public Boolean getAccComplete() {
        return accComplete;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserDisplayname(String userDisplayname) {
        this.userDisplayname = userDisplayname;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public void setUserHeight(String userHeight) {
        this.userHeight = userHeight;
    }

    public void setUserWeight(String userWeight) {
        this.userWeight = userWeight;
    }

    public void setUserBirthdate(String userBirthdate) {
        this.userBirthdate = userBirthdate;
    }

    public void setAccComplete(Boolean accComplete) {
        this.accComplete = accComplete;
    }
}
