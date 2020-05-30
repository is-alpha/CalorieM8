package com.dingo.caloriem8;

public class DayInfo {
    private String date;
    private String calsConsumed;
    private String calsBurned;
    private String avgSleep;
    private String extraSleep;
    private String weight;

    public DayInfo() {
        this.date = "null";
        this.calsConsumed = "null";
        this.calsBurned = "null";
        this.avgSleep = "null";
        this.extraSleep = "null";
        this.weight = "null";
    }

    public DayInfo(DayInfo dayInfo) {
        this.date = dayInfo.date;
        this.calsConsumed = dayInfo.calsConsumed;
        this.calsBurned = dayInfo.calsBurned;
        this.avgSleep = dayInfo.avgSleep;
        this.extraSleep = dayInfo.extraSleep;
        this.weight = dayInfo.weight;
    }

    public DayInfo(String date, String calsConsumed, String calsBurned, String avgSleep, String extraSleep, String weight) {
        this.date = date;
        this.calsConsumed = calsConsumed;
        this.calsBurned = calsBurned;
        this.avgSleep = avgSleep;
        this.extraSleep = extraSleep;
        this.weight = weight;
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

    public void setWeight(String weight) {
        this.weight = weight;
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

    public String getWeight() {
        return weight;
    }
}
