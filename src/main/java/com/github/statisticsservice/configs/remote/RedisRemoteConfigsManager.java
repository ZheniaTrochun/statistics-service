package com.github.statisticsservice.configs.remote;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;
import java.util.Map;


@Component
public class RedisRemoteConfigsManager implements RemoteConfigsManager {

    @Value("${redis.url}")
    private String redis;

    @Value("${config.key}")
    private String KEY;

    private Jedis jedis;

    @PostConstruct
    void init() {
        jedis = new Jedis(redis);
    }

    @Override
    public Map<String, String> getRemoteConfig() {

        return jedis.hgetAll(KEY);
    }

    @Override
    public void setRemoteConfig(String key, String value) {
        jedis.hset(KEY, key, value);
    }

    @Override
    public void createDummyConfig() {

    }
}
