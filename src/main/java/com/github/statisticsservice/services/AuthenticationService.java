package com.github.statisticsservice.services;

public interface AuthenticationService {

    /**
     * Checks if statistics of User with such username exists.
     *
     * @param username unique username of needed User.
     * @return true, if statistics exists.
     */
    boolean userIsPresent(String username);

}
