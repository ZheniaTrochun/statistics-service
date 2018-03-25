package com.github.statisticsservice.configs;

import com.github.statisticsservice.configs.remote.RemoteConfigsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@ConfigurationProperties
public class AppPropertyHolder implements PropertyHolder {

    private final RemoteConfigsManager remoteManager;

    private Map<String, String> remoteConfigs;

    private final Environment env;

    @Autowired
    public AppPropertyHolder(RemoteConfigsManager remoteManager, Environment env) {
        this.remoteManager = remoteManager;
        this.env = env;
        remoteConfigs = remoteManager.getRemoteConfig();
    }

    @Override
    public String get(String key) {

        return Optional.of(remoteConfigs.get(key))
                .orElse(env.getProperty(key, ""));
    }
}
