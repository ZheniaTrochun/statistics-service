package com.github.statisticsservice.repository;

import com.github.statisticsservice.model.Statistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StatisticsRepository extends MongoRepository<Statistics, String> {

    Optional<Statistics> findByUserAndYear(String user, Integer year);
}
