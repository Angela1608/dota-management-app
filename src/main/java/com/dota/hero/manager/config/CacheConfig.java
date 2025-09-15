package com.dota.hero.manager.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Value("${spring.cache.ttl}")
    private long cacheDuration;

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        CaffeineCache heroCache = new CaffeineCache("heroes-cache",
                Caffeine.newBuilder()
                        .expireAfterWrite(cacheDuration, TimeUnit.HOURS)
                        .build());


        CaffeineCache itemCache = new CaffeineCache("items-cache",
                Caffeine.newBuilder()
                        .expireAfterWrite(cacheDuration, TimeUnit.HOURS)
                        .build());

        cacheManager.setCaches(List.of(heroCache, itemCache));

        return cacheManager;
    }

}
