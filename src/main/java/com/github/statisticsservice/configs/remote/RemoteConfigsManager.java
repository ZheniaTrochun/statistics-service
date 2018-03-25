package com.github.statisticsservice.configs.remote;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Map;

public interface RemoteConfigsManager {

    Map<String, String> getRemoteConfig();
    void setRemoteConfig(String key, String value);
    void createDummyConfig();
}
