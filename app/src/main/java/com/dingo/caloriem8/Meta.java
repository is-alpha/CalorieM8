package com.dingo.caloriem8;

public class Meta {

    private String startDate;
    private String calorias;
    private String steps;

    public Meta(){

    }

    public Meta(String startDate, String calorias, String steps) {
        this.startDate = startDate;
        this.calorias = calorias;
        this.steps = steps;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCalorias() {
        return calorias;
    }

    public void setCalorias(String calorias) {
        this.calorias = calorias;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

}
