package com.dingo.caloriem8;

public class Ejercicio {

    private String exercise;
    private String date;
    private String start_Time;
    private String end_Time;
    private String burned_Calories;

    public Ejercicio(){
        exercise = "0";
        date = "0";
        start_Time = "0";
        end_Time = "0";
        burned_Calories = "0";
    }

    public Ejercicio(String exercise, String date, String start_Time, String end_Time, String burned_Calories) {
        this.exercise = exercise;
        this.date = date;
        this.start_Time = start_Time;
        this.end_Time = end_Time;
        this.burned_Calories = burned_Calories;
    }

    public Ejercicio(String exercise) {
        this.exercise = exercise;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_Time() {
        return start_Time;
    }

    public void setStart_Time(String start_Time) {
        this.start_Time = start_Time;
    }

    public String getEnd_Time() {
        return end_Time;
    }

    public void setEnd_Time(String end_Time) {
        this.end_Time = end_Time;
    }

    public String getBurned_Calories() {
        return burned_Calories;
    }

    public void setBurned_Calories(String burned_Calories) {
        this.burned_Calories = burned_Calories;
    }

    public String toString(){
        return exercise;
    }

}
