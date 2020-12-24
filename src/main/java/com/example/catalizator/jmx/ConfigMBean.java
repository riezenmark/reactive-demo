package com.example.catalizator.jmx;

import com.example.catalizator.service.ConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
@ManagedResource(description = "Application config bean")
public class ConfigMBean {
    private final ConfigService configService;

    @ManagedAttribute(description = "Set reload attribute")
    public void setReloadPeriod(long reloadPeriod) {
        configService.setReloadPeriod(reloadPeriod);
    }

    @ManagedAttribute(description = "Get reload attribute")
    public long getReloadPeriod() {
        return configService.getReloadPeriod();
    }

    @ManagedOperation(description = "Get all config keys")
    public Collection<String> getConfigKeys() {
        return configService.configKeys();
    }

    @ManagedOperation(description = "Get config value")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "key", description = "Config key")
    })
    public String getConfig(String key) {
        return configService.getConfig(key);
    }

    @ManagedOperation(description = "Set config value")
    @ManagedOperationParameters({
            @ManagedOperationParameter(name = "key", description = "Config key"),
            @ManagedOperationParameter(name = "value", description = "Target config value")
    })
    public void setConfig(String key, String value) {
        configService.setConfig(key, value);
    }
}
