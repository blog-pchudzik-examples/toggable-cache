package com.pchudzik.blog.example.toggablecache.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class Config {
    @Value("${cache.redis.host}")
    private String host;

    @Value("${cache.redis.port}")
    private int port;

    @Bean
    public ToggleProvider toggleProvider() {
        return new ToggleProvider();
    }

    @Bean("cacheManager")
    public CacheManager cacheManager() {
        return new ToggleAwareCacheManager(
                toggleProvider(),
                RedisCacheManager.create(lettuceConnectionFactory()));
    }

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }
}
