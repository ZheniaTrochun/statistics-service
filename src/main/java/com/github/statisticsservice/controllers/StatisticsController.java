package com.github.statisticsservice.controllers;

import com.github.statisticsservice.model.Statistics;
import com.github.statisticsservice.model.StatisticsData;
import com.github.statisticsservice.services.AuthenticationService;
import com.github.statisticsservice.services.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Main Application's REST controller.
 *
 * Serves main Application's endpoints.
 *
 * @version 0.1
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

    private final StatisticsService statisticsService;
    private final AuthenticationService authenticationService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService,
                                AuthenticationService authenticationService) {
        this.statisticsService = statisticsService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/incomes/per-month")
    public @ResponseBody Map<String, Double> getAllIncomesPerMonth(
            @RequestParam Integer year,
            @RequestParam Integer month,
            HttpRequest request) throws AuthenticationException {
        LOGGER.trace("Trying to get all incomes per month...");

        String username = checkAuthentication(request);     // TODO throws AuthenticationException!

        Statistics statistics = statisticsService.getPerMonth(username, year, month);

        LOGGER.trace("Successfully got all incomes per month.");
        return statistics.getIncomesStats();
    }

    @GetMapping("/incomes/percentage")
    public @ResponseBody Map<String, Integer> getPercentageIncomesPerAllTime(HttpRequest request)
            throws AuthenticationException {
        LOGGER.trace("Trying to get percentages of all incomes...");

        String username = checkAuthentication(request);     // TODO throws AuthenticationException!

        LOGGER.trace("Successfully got percentages of all incomes.");
        return statisticsService.getIncomesPercentage(username);
    }

    @GetMapping("/outcomes/per-month")
    public @ResponseBody Map<String, Double> getAllOutcomesPerMonth(
            @RequestParam Integer year,
            @RequestParam Integer month,
            HttpRequest request) throws AuthenticationException {
        LOGGER.trace("Trying to get all outcomes per month...");

        String username = checkAuthentication(request);     // TODO throws AuthenticationException!

        Statistics statistics = statisticsService.getPerMonth(username, year, month);

        LOGGER.trace("Successfully got all outcomes per month.");
        return statistics.getOutcomesStats();
    }

    @GetMapping("/outcomes/percentage")
    public @ResponseBody Map<String, Integer> getPercentageOutcomesPerAllTime(HttpRequest request)
            throws AuthenticationException {
        LOGGER.trace("Trying to get percentages of all outcomes...");

        String username = checkAuthentication(request);     // TODO throws AuthenticationException!

        LOGGER.trace("Successfully got percentages of all outcomes.");
        return statisticsService.getOutcomesPercentage(username);
    }



    @PostMapping("/update/incomes")
    public ResponseEntity updateIncomes(@RequestBody StatisticsData data, HttpRequest request)
            throws AuthenticationException {
        LOGGER.trace("Trying to update statistics of incomes...");

        String username = checkAuthentication(request);     // TODO throws AuthenticationException!

        LocalDateTime now = LocalDateTime.now();

        statisticsService.updateIncomesStats(username, now.getYear(), now.getMonthValue(), data);

        LOGGER.trace("Successfully updated statistics of incomes.");
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/update/outcomes")
    public ResponseEntity updateOutcomes(@RequestBody StatisticsData data, HttpRequest request)
            throws AuthenticationException {
        LOGGER.trace("Trying to update statistics of outcomes...");

        String username = checkAuthentication(request);     // TODO throws AuthenticationException!

        LocalDateTime now = LocalDateTime.now();

        statisticsService.updateOutcomesStats(username, now.getYear(), now.getMonthValue(), data);

        LOGGER.trace("Successfully updated statistics of outcomes.");
        return new ResponseEntity(HttpStatus.OK);
    }


    /**
     * Checks if request is authorised.
     *
     * @param request incoming HttpRequest.
     * @return valid username of existent User, if request is authorised.
     * @throws AuthenticationException if request was not authorised.
     */
    private String checkAuthentication(HttpRequest request) throws AuthenticationException {
        LOGGER.trace("Checking authentication...");

        List<String> headers = request.getHeaders().get("Authentication");

        if (headers.isEmpty()) {
            throw new AuthenticationException("Not authorised!");
        } else {
            String username = headers.get(0);

            if (!authenticationService.userIsPresent(username)) {
                throw new AuthenticationException("Not authorised!");
            }

            LOGGER.trace("Authentication was checked successfully.");
            return username;
        }
    }
}
