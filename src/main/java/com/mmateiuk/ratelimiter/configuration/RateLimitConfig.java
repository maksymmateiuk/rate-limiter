package com.mmateiuk.ratelimiter.configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class RateLimitConfig {

    @Value("${ratelimit.capacity}")
    private long capacity;
    @Value("${ratelimit.tokens}")
    private long tokens;

    @Value("${ratelimit.period}")
    private long period;

    private final List<String> hasRestrictionRatesLimit = Arrays.asList("test", "test2");

    private Bandwidth getDefaultLimits() {
        return Bandwidth.classic(capacity, Refill.intervally(tokens, Duration.ofSeconds(period)));
    }

    public Bandwidth resolveLimit(String accountId) {
        if (hasRestrictionRatesLimit.contains(accountId)) {
           return Bandwidth.classic(2, Refill.intervally(2, Duration.ofSeconds(10)));
        }

        // return default limit
        return getDefaultLimits();
    }

}
