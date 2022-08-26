package com.mmateiuk.ratelimiter.controllers;

import com.mmateiuk.ratelimiter.service.RateLimitService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import javax.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ByAccountRateLimitController {

    private RateLimitService rateLimitService;

    public ByAccountRateLimitController(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @GetMapping("/api/v1/{accountId}/hello2")
    public ResponseEntity<String> hello(@PathParam("accountId") String accountId) {

        Bucket bucket = rateLimitService.resolveBucket(accountId);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (!probe.isConsumed()) {
            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .header("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill))
                    .build();
        }

        return ResponseEntity.ok("Hello World!");
    }
}
