package com.github.statisticsservice.services;

import com.github.statisticsservice.model.Statistics;
import com.github.statisticsservice.model.StatisticsData;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Main Statistics Service interface.
 *
 * Holds main business logic of Application.
 *
 * @version 0.1
 */
@Service
public interface StatisticsService {

    /**
     * Gets part of statistics for target month of year.
     *
     * @param username valid and unique username of existent User.
     * @param year target statistics part's year.
     * @param month target statistics part's month of year.
     * @return Statistics object, if it is stored in Persistence, or null, if it isn't
     */
    Statistics getPerMonth(String username, Integer year, Integer month);

    /**
     * Gets statistics of incomes for all time percentage (by tags).
     *
     * @param username valid and unique username of existent User.
     * @return needed incomes statistics map (key - tag, value - percentage), if statistics is present,
     * or empty Map, if it is not present.
     */
    Map<String,Integer> getIncomesPercentage(String username);

    /**
     * Gets statistics of outcomes for all time percentage (by tags).
     *
     * @param username valid and unique username of existent User.
     * @return needed outcomes statistics map (key - tag, value - percentage), if statistics is present,
     * or empty Map, if it is not present.
     */
    Map<String,Integer> getOutcomesPercentage(String username);

    /**
     * Updates incomes statistics with new data.
     *
     * If requested incomes statistics is not stored at the moment, new part of
     * incomes statistics (with proposed new data) will be saved to Persistence.
     *
     * @param username valid and unique username of existent User.
     * @param year target statistics part's year.
     * @param month target statistics part's month.
     * @param data new data, which will be used to update incomes statistics.
     * @return updated Statistics instance.
     */
    Statistics updateIncomesStats(String username, Integer year, Integer month, StatisticsData data);

    /**
     * Updates outcomes statistics with new data.
     *
     * If requested outcomes statistics is not stored at the moment, new part of
     * outcomes statistics (with proposed new data) will be saved to Persistence.
     *
     * @param username valid and unique username of existent User.
     * @param year target statistics part's year.
     * @param month target statistics part's month.
     * @param data new data, which will be used to update outcomes statistics.
     */
    Statistics updateOutcomesStats(String username, Integer year, Integer month, StatisticsData data);
}
