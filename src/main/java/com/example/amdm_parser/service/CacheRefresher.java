package com.example.amdm_parser.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheRefresher {
    private final CacheManager cacheManager;

    public CacheRefresher(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Scheduled(cron = "0 5 2 * * ?",zone = "GMT+5")
    @CacheEvict(value = "Songs", allEntries = true)
    public void clearCache() {
        cacheManager.getCache("Songs").clear();
        log.info("Clearing cache...");
    }
}
