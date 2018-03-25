package com.github.statisticsservice.services;

import com.github.statisticsservice.model.Statistics;
import org.springframework.stereotype.Service;

@Service
public interface StatisticsService {
    Statistics getStatisticsByYear(String user, Integer year);
}
