package com.dingo.caloriem8;

public class DayInfo {
    private String date;
    private String calsConsumed;
    private String calsBurned;
    private String avgSleep;
    private String extraSleep;

    public DayInfo() {
        this.date = "null";
        this.calsConsumed = "null";
        this.calsBurned = "null";
        this.avgSleep = "null";
        this.extraSleep = "null";
    }

    public DayInfo(String date, String calsConsumed, String calsBurned, String avgSleep, String extraSleep) {
        this.date = date;
        this.calsConsumed = calsConsumed;
        this.calsBurned = calsBurned;
        this.avgSleep = avgSleep;
        this.extraSleep = extraSleep;
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

    public void setAvgSleep(String avgSleep) {
        this.avgSleep = avgSleep;
    }

    public void setExtraSleep(String extraSleep) {
        this.extraSleep = extraSleep;
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

    public String getAvgSleep() {
        return avgSleep;
    }

    public String getExtraSleep() {
        return extraSleep;
    }
}
