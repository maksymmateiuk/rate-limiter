package com.mmateiuk.ratelimiter.annotation;

import com.mmateiuk.ratelimiter.exceptions.RateLimitReachedException;
import com.mmateiuk.ratelimiter.service.RateLimitService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RateLimitedAspect {

    private final RateLimitService rateLimitService;

    public RateLimitedAspect(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Before("@annotation(rateLimited)")
    public void checkRateLimit(RateLimited rateLimited) {
        String accountId = rateLimited.entityParam();
        Bucket tokenBucket = rateLimitService.resolveBucket(accountId);
        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);
        if (!probe.isConsumed()) {
            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
            throw new RateLimitReachedException(waitForRefill);
        }
    }

}
