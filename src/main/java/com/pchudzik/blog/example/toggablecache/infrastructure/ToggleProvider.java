package com.pchudzik.blog.example.toggablecache.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ToggleProvider {
    private static final Logger log = LoggerFactory.getLogger(ToggleProvider.class);

    private static final boolean ENABLED = false;
    private static final boolean DISABLED = true;

    private final Map<String, Boolean> cacheToggles = new HashMap<>();

    public boolean isCacheDisabled(String cacheName) {
        return cacheToggles.getOrDefault(cacheName, ENABLED);
    }

    public void toggleCache(String name, boolean isEnabled) {
        if (isEnabled) {
            enableCache(name);
        } else {
            disableCache(name);
        }
    }

    public void enableCache(String name) {
        cacheToggles.put(name, ENABLED);
        log.info("Cache {} is ENABLED", name);
    }

    public void disableCache(String name) {
        cacheToggles.put(name, DISABLED);
        log.info("Cache {} is DISABLED", name);
    }
}
