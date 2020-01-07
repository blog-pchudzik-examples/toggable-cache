package com.pchudzik.blog.example.toggablecache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachedService {
    private static final Logger log = LoggerFactory.getLogger(CachedService.class);

    @Cacheable(Caches.FIRST)
    public String getValue(int index) {
        log.info("Computing value for {}", index);
        Sleeper.sleep(200);
        return "value " + index;
    }
}
