package com.dingo.caloriem8;

public class Food {
    private int id;
    private String name;
    private int serving;
    private int calories;
    private float fat;
    private float carbs;
    private float fiber;
    private float protein;

    public Food() {

    }

    public Food(int id, String name, int serving, int calories, float fat, float carbs, float fiber, float protein) {
        this.id = id;
        this.name = name;
        this.serving = serving;
        this.calories = calories;
        this.fat = fat;
        this.carbs = carbs;
        this.fiber = fiber;
        this.protein = protein;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServing(int serving) {
        this.serving = serving;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServing() {
        return serving;
    }

    public int getCalories() {
        return calories;
    }

    public float getFat() {
        return fat;
    }

    public float getCarbs() {
        return carbs;
    }

    public float getFiber() {
        return fiber;
    }

    public float getProtein() {
        return protein;
    }
}
