package com.github.statisticsservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Map;
import java.util.Objects;

public class Statistics {

    @Id
    private String id;

    @Indexed(unique = true)
    private String user;
    @Indexed(unique = true)
    private Integer year;

    private Map<String, Double> stats;

    public Statistics() {
    }

    public Statistics(String id, String user, Integer year, Map<String, Double> stats) {
        this.id = id;
        this.user = user;
        this.year = year;
        this.stats = stats;
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

    public Map<String, Double> getStats() {
        return stats;
    }

    public void setStats(Map<String, Double> stats) {
        this.stats = stats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Statistics)) return false;
        Statistics that = (Statistics) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getYear(), that.getYear()) &&
                Objects.equals(getStats(), that.getStats());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getUser(), getYear(), getStats());
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", year=" + year +
                ", stats=" + stats +
                '}';
    }
}
