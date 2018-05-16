package com.github.statisticsservice.services;

import com.github.statisticsservice.repository.StatisticsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final StatisticsRepository statisticsRepository;

    @Autowired
    public AuthenticationServiceImpl(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    /**
     * Checks if statistics of User with such username exists.
     *
     * @param username unique username of needed User.
     * @return true, if statistics exists.
     */
    @Override
    public boolean userIsPresent(String username) {
        LOGGER.trace("Checking if user was registered to system...");

        // validation...

        return statisticsRepository.existsByUser(username);
    }
}
