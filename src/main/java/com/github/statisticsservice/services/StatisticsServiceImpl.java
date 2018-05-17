package com.github.statisticsservice.services;

import com.github.statisticsservice.model.Statistics;
import com.github.statisticsservice.model.StatisticsData;
import com.github.statisticsservice.repository.StatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of Main Statistics Service Interface.
 *
 * @version 0.1
 *
 * @see StatisticsService
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    private final StatisticsRepository statisticsRepository;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    /**
     * Gets part of statistics for target month of year.
     *
     * @param username valid and unique username of existent User.
     * @param year     target statistics part's year.
     * @param month    target statistics part's month of year.
     * @return Statistics object, if it is stored in Persistence, or null, if it isn't
     */
    @Override
    public Statistics getPerMonth(String username, Integer year, Integer month) {
        LOGGER.trace("Getting whole statistics per month...");

        Optional<Statistics> optionalStatistics =
                statisticsRepository.findByUserAndYearAndMonth(username, year, month);

        if (!optionalStatistics.isPresent()) {
            LOGGER.trace("Such statistics doesn't exist. Wrong search keys (user, year, or month).");
            return null;
        } else {
            LOGGER.trace("Successfully got whole statistics per month.");
            return optionalStatistics.get();
        }
    }

    /**
     * Gets statistics of incomes for all time percentage (by tags).
     *
     * @param username valid and unique username of existent User.
     * @return needed incomes statistics map (key - tag, value - percentage), if statistics is present,
     * or empty Map, if it is not present.
     */
    @Override
    public Map<String, Integer> getIncomesPercentage(String username) {
        LOGGER.trace("Getting incomes statistics' percentage...");

        List<Statistics> statisticsList =
                statisticsRepository.findAllByUser(username);

        if (statisticsList == null || statisticsList.isEmpty()) {
            LOGGER.trace("No statistics found for such User.");
            return new HashMap<>();
        }

        List<Map<String, Double>> statsMapList = statisticsList.stream()
                .map(Statistics::getIncomesStats)
                .collect(Collectors.toList());

        LOGGER.trace("Successfully got incomes statistics' percentage.");
        return mapStatsToPercentage(statsMapList);
    }

    /**
     * Gets statistics of outcomes for all time percentage (by tags).
     *
     * @param username valid and unique username of existent User.
     * @return needed outcomes statistics map (key - tag, value - percentage), if statistics is present,
     * or empty Map, if it is not present.
     */
    @Override
    public Map<String, Integer> getOutcomesPercentage(String username) {
        LOGGER.trace("Getting outcomes statistics' percentage...");

        List<Statistics> statisticsList =
                statisticsRepository.findAllByUser(username);

        if (statisticsList == null || statisticsList.isEmpty()) {
            LOGGER.trace("No statistics found for such User.");
            return new HashMap<>();
        }

        List<Map<String, Double>> statsMapList = statisticsList.stream()
                .map(Statistics::getOutcomesStats)
                .collect(Collectors.toList());

        LOGGER.trace("Successfully got outcomes statistics' percentage.");
        return mapStatsToPercentage(statsMapList);
    }

    /**
     * Updates incomes statistics with new data.
     * <p>
     * If requested incomes statistics is not stored at the moment, new part of
     * incomes statistics (with proposed new data) will be saved to Persistence.
     *
     * @param username valid and unique username of existent User.
     * @param year     target statistics part's year.
     * @param month    target statistics part's month.
     * @param data     new data, which will be used to update incomes statistics.
     * @return updated Statistics instance.
     */
    @Override
    public Statistics updateIncomesStats(String username, Integer year, Integer month, StatisticsData data) {
        LOGGER.trace("Updating incomes statistics per month...");

        Optional<Statistics> statistics = statisticsRepository.findByUserAndYearAndMonth(username, year, month);

        if (statistics.isPresent()) {
            Map<String, Double> incomesStats = statistics.get().getIncomesStats();

            if (incomesStats.containsKey(data.getTag())) {
                incomesStats.put(data.getTag(), incomesStats.get(data.getTag()) + data.getData());
            } else {
                incomesStats.put(data.getTag(), data.getData());
            }

            statisticsRepository.save(statistics.get());

            LOGGER.trace("Successfully updated incomes statistics per month.");
            return statistics.get();
        } else {
            Statistics newStatistics = new Statistics(username, year, month, new HashMap<>(), new HashMap<>());
            newStatistics.getIncomesStats().put(data.getTag(), data.getData());

            statisticsRepository.save(newStatistics);

            LOGGER.trace("Successfully created new statistics record instead of update existent.");
            return newStatistics;
        }
    }

    /**
     * Updates outcomes statistics with new data.
     * <p>
     * If requested outcomes statistics is not stored at the moment, new part of
     * outcomes statistics (with proposed new data) will be saved to Persistence.
     *
     * @param username valid and unique username of existent User.
     * @param year     target statistics part's year.
     * @param month    target statistics part's month.
     * @param data     new data, which will be used to update outcomes statistics.
     */
    @Override
    public Statistics updateOutcomesStats(String username, Integer year, Integer month, StatisticsData data) {
        LOGGER.trace("Updating outcomes statistics per month...");

        Optional<Statistics> statistics = statisticsRepository.findByUserAndYearAndMonth(username, year, month);
        if (statistics.isPresent()) {
            Map<String, Double> outcomesStats = statistics.get().getOutcomesStats();

            if (outcomesStats.containsKey(data.getTag())) {
                outcomesStats.put(data.getTag(), outcomesStats.get(data.getTag()) + data.getData());
            } else {
                outcomesStats.put(data.getTag(), data.getData());
            }

            statisticsRepository.save(statistics.get());

            LOGGER.trace("Successfully updated outcomes statistics per month.");
            return statistics.get();
        } else {
            Statistics newStatistics = new Statistics(username, year, month, new HashMap<>(), new HashMap<>());
            newStatistics.getOutcomesStats().put(data.getTag(), data.getData());

            statisticsRepository.save(newStatistics);

            LOGGER.trace("Successfully created new statistics record instead of update existent.");
            return newStatistics;
        }
    }

    /**
     * Maps list of statistics Maps to Map of statistics' percentages.
     *
     * @param mapList target List of statistics Maps.
     * @return Map, where key is tag and value is percentage.
     */
    private Map<String, Integer> mapStatsToPercentage(List<Map<String, Double>> mapList) {
        Map<String, Double> mergedMap = new HashMap<>();

        mapList.forEach(incomesStats -> incomesStats.forEach((key, value) ->
                mergedMap.put(key, mergedMap.containsKey(key) ? (mergedMap.get(key) + value) : value)));

        double sum = mergedMap.entrySet().stream().mapToDouble(Map.Entry::getValue).sum();

        Map<String, Integer> resultMap = new HashMap<>();

        mergedMap.forEach((key, value) -> resultMap.put(key, (int) Math.round(value / sum * 100)));

        return resultMap;
    }
}
