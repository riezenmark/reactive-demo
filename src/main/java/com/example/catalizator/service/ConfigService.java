package com.example.catalizator.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigService {
    @Getter
    @Setter
    private long reloadPeriod = 300L;
    private final Map<String, String> config = new HashMap<>();

    public Collection<String> configKeys() {
        return new ArrayList<>(config.keySet());
    }

    public String getConfig(String key) {
        return config.get(key);
    }

    public void setConfig(String key, String value) {
        config.put(key, value);
    }
}
