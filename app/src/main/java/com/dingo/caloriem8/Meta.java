package com.dingo.caloriem8;

public class Meta {

    private String steps;
    private String consumedCalories;
    private String burnedCalories;

    public Meta(){

    }

    public Meta(String steps, String consumedCalories, String burnedCalories) {
        this.steps = steps;
        this.consumedCalories = consumedCalories;
        this.burnedCalories = burnedCalories;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getConsumedCalories() {
        return consumedCalories;
    }

    public void setConsumedCalories(String consumedCalories) { this.consumedCalories = consumedCalories; }

    public String getBurnedCalories() {
        return burnedCalories;
    }

    public void setBurnedCalories(String burnedCalories) {
        this.burnedCalories = burnedCalories;
    }

}
