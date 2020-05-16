package com.dingo.caloriem8;

public class Day {
    private String date;
    private String calsConsumed;
    private String calsBurned;

    public Day() {
    }

    public void setCalsConsumed(String calsConsumed) {
        this.calsConsumed = calsConsumed;
    }

    public void setCalsBurned(String calsBurned) {
        this.calsBurned = calsBurned;
    }

    public String getCalsConsumed() {
        return calsConsumed;
    }

    public String getCalsBurned() {
        return calsBurned;
    }
}
