package com.pchudzik.blog.example.toggablecache;

import com.pchudzik.blog.example.toggablecache.infrastructure.ToggleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.stream.Stream;

@SpringBootApplication
@EnableCaching()
public class ToggableCacheApplication {
    private static final Logger log = LoggerFactory.getLogger(ToggableCacheApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ToggableCacheApplication.class, args);
        ToggleProvider toggle = ctx.getBean(ToggleProvider.class);
        CachedService service = ctx.getBean(CachedService.class);

        boolean isCacheEnabled = false;
        toggle.disableCache(Caches.FIRST);

        for (int runs = 0; runs < 5; runs++) {
            Stream
                    .of(1, 2, 3, 4, 5)
                    .forEach(i -> measure(() -> service.getValue(i)));

            isCacheEnabled = !isCacheEnabled;
            toggle.toggleCache(Caches.FIRST, isCacheEnabled);
        }
    }

    public static void measure(Runnable action) {
        long start = System.currentTimeMillis();
        try {
            action.run();
        } finally {
            long end = System.currentTimeMillis();
            log.info("Loading value took {}ms", (end - start));
        }
    }
}
