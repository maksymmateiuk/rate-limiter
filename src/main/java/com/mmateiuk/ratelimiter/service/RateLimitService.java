package com.mmateiuk.ratelimiter.service;

import com.mmateiuk.ratelimiter.configuration.RateLimitConfig;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class RateLimitService {

    private final RateLimitConfig rateLimitConfig;

    public RateLimitService(RateLimitConfig rateLimitConfig) {
        this.rateLimitConfig = rateLimitConfig;
    }

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String accountId) {
        return cache.computeIfAbsent(accountId, this::newBucket);
    }

    private Bucket newBucket(String accountId) {
        Bandwidth limits = rateLimitConfig.resolveLimit(accountId);
        return Bucket.builder()
                .addLimit(limits)
                .build();
    }
}
