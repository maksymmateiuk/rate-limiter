package com.mmateiuk.ratelimiter.controllers;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleRateLimitController {

    private final Bucket bucket;

    public SimpleRateLimitController() {
        // 2 request per 15 second, will be 1 request per 7,5 second
        Bandwidth limit = Bandwidth.classic(2, Refill.intervally(2, Duration.ofSeconds(15)));
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping("/api/v1/hello")
    public ResponseEntity<String> hello() {
        // when bucket doesn't have tokens return too many requests
        if (!bucket.tryConsume(1)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        return ResponseEntity.ok("Hello World!");
    }
}
