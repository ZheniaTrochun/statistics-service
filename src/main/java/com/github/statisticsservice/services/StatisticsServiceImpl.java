package com.github.statisticsservice.services;

import com.github.statisticsservice.configs.PropertyHolder;
import com.github.statisticsservice.model.Statistics;
import com.github.statisticsservice.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;
    private final PropertyHolder propertyHolder;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepository statisticsRepository, PropertyHolder propertyHolder) {
        this.statisticsRepository = statisticsRepository;
        this.propertyHolder = propertyHolder;
    }


    @Override
    public Statistics getStatisticsByYear(String user, Integer year) {
        Optional<Statistics> stats = statisticsRepository.findByUserAndYear(user, year);

        if (stats.isPresent()) {

            return stats.get();
        } else {
            throw new InvalidParameterException("error!");
        }
    }
}
