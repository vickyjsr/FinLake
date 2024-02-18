package com.finlake.common.cache;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@Component
public class ConfigurationRefreshCacheWorker extends RefreshCache {

    private final ResponseMapperCache responseMapperCache;
    private final ScheduledExecutorService scheduledExecutorService;

    @Value("${cache.refresh.interval.seconds:3600}")
    private int refreshInterval;

    public ConfigurationRefreshCacheWorker(ResponseMapperCache responseMapperCache) {
        this.responseMapperCache = responseMapperCache;
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @PostConstruct
    private void init() {
        try {
            refreshCache();
        } catch (Exception e) {
            log.error("Exception {} while initializing cache", e);
        }
        scheduledExecutorService.schedule(this, getRefreshInterval(), TimeUnit.SECONDS);
    }

    private Integer getRefreshInterval() {
        return refreshInterval;
    }

    @Override
    public void refreshCache() {
        try {
            log.info("Refreshing cache for ResponseMapperCache");
            responseMapperCache.refresh();
            log.info("Cache Refreshed for ResponseMapperCache");
        } catch (Exception e) {
            log.error("Exception {} while refreshing cache", e);
            throw e;
        }
    }
}
