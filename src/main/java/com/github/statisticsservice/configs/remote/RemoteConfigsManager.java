package com.github.statisticsservice.configs.remote;

import java.util.Map;

public interface RemoteConfigsManager {

    Map<String, String> getRemoteConfig();
    void setRemoteConfig(String key, String value);
    void createDummyConfig();
}
