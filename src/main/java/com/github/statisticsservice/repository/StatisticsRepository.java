package com.github.statisticsservice.repository;

import com.github.statisticsservice.model.Statistics;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Main Statistics repository. Based on Spring Data's MongoRepository.
 *
 * @version 0.1
 *
 * @see MongoRepository
 */
public interface StatisticsRepository extends MongoRepository<Statistics, String> {

    Optional<Statistics> findByUserAndYearAndMonth(String user, Integer year, Integer month);

    List<Statistics> findAllByUser(String user);

    boolean existsByUser(String user);
}
