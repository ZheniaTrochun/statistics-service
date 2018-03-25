package com.github.statisticsservice.controllers;

import com.github.statisticsservice.model.Statistics;
import com.github.statisticsservice.services.StatisticsService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Map;

@Controller
public class StatisticsController {

    private final StatisticsService statisticsService;

//    TODO replace
    @Value("${api.secret}")
    private String secret;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/statistics")
    public @ResponseBody Map<String, Double> getStatistics(@RequestParam Integer year, HttpRequest request) throws AuthenticationException {
        List<String> headers = request.getHeaders().get("Authentication");

        if (headers.isEmpty()) {
            throw new AuthenticationException("Not authorised!");
        } else {
            String jwt = headers.get(0);

            String user = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody()
                    .get("user", String.class);

            Statistics statistics = statisticsService.getStatisticsByYear(user, year);

            return statistics.getStats();
        }
    }
}
