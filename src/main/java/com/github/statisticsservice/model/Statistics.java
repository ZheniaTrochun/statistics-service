package com.github.statisticsservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Objects;

/**
 * Statistics domain (POJO data class).
 *
 * Contains field declarations, needed for MongoDB.
 *
 * @version 0.1
 */
@Document(collection = "statistics")
public class Statistics {

    @Id
    private String id;

    @Indexed(unique = true)
    private String user;
    @Indexed(unique = true)
    private Integer year;
    @Indexed(unique = true)
    private Integer month;

    private Map<String, Double> incomesStats;
    private Map<String, Double> outcomesStats;

    public Statistics() {
    }

    public Statistics(String id, String user, Integer year, Integer month,
                      Map<String, Double> incomesStats, Map<String, Double> outcomesStats) {
        this.id = id;
        this.user = user;
        this.year = year;
        this.month = month;
        this.incomesStats = incomesStats;
        this.outcomesStats = outcomesStats;
    }

    public Statistics(String user, Integer year, Integer month, Map<String, Double> incomesStats, Map<String, Double> outcomesStats) {
        this.user = user;
        this.year = year;
        this.month = month;
        this.incomesStats = incomesStats;
        this.outcomesStats = outcomesStats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Map<String, Double> getIncomesStats() {
        return incomesStats;
    }

    public void setIncomesStats(Map<String, Double> incomesStats) {
        this.incomesStats = incomesStats;
    }

    public Map<String, Double> getOutcomesStats() {
        return outcomesStats;
    }

    public void setOutcomesStats(Map<String, Double> outcomesStats) {
        this.outcomesStats = outcomesStats;
    }



    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getYear(), getMonth(),
                getIncomesStats(), getOutcomesStats());
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", incomesStats=" + incomesStats +
                ", outcomesStats=" + outcomesStats +
                '}';
    }
}
