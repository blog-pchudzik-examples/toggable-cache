package com.pchudzik.blog.example.toggablecache.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ToggleAwareCacheManager implements CacheManager {
    private static final Logger log = LoggerFactory.getLogger(ToggleAwareCacheManager.class);

    private final ToggleProvider toggleProvider;
    private final CacheManager wrapped;
    private final CacheManager noOpCacheManager;

    ToggleAwareCacheManager(ToggleProvider toggleProvider, CacheManager wrapped) {
        this.noOpCacheManager = new NoOpCacheManager();
        this.toggleProvider = toggleProvider;
        this.wrapped = wrapped;
    }

    @Override
    public Cache getCache(String s) {
        if (toggleProvider.isCacheDisabled(s)) {
            return noOpCacheManager.getCache(s);
        }

        return wrapped.getCache(s);
    }

    @Override
    public Collection<String> getCacheNames() {
        return Stream
                .concat(noOpCacheManager.getCacheNames().stream(), wrapped.getCacheNames().stream())
                .collect(Collectors.toSet());
    }
}
