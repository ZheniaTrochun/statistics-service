package com.github.statisticsservice.model;

/**
 * Additional model (POJO data class).
 *
 * Used to work with data of update-statistics operation.
 *
 * @version 0.1
 */
public class StatisticsData {

    private String tag;
    private String user;
    private Double data;

    public StatisticsData() {
    }

    public StatisticsData(String tag, Double data) {
        this.tag = tag;
        this.data = data;
    }

    public StatisticsData(String tag, String user, Double data) {
        this.tag = tag;
        this.user = user;
        this.data = data;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Double getData() {
        return data;
    }

    public void setData(Double data) {
        this.data = data;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
