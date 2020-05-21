package com.dingo.caloriem8;

public class DayInfo {
    private String date;
    private String calsConsumed;
    private String calsBurned;

    public DayInfo() {
    }

    public DayInfo(String date, String calsConsumed, String calsBurned) {
        this.date = date;
        this.calsConsumed = calsConsumed;
        this.calsBurned = calsBurned;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCalsConsumed(String calsConsumed) {
        this.calsConsumed = calsConsumed;
    }

    public void setCalsBurned(String calsBurned) {
        this.calsBurned = calsBurned;
    }

    public String getDate() {
        return date;
    }

    public String getCalsConsumed() {
        return calsConsumed;
    }

    public String getCalsBurned() {
        return calsBurned;
    }
}
