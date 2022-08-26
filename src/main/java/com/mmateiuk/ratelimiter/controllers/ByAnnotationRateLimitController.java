package com.mmateiuk.ratelimiter.controllers;

import com.mmateiuk.ratelimiter.annotation.RateLimited;
import javax.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ByAnnotationRateLimitController {

    @GetMapping("/api/v3/{accountId}/hello")
    @RateLimited(entityParam = "123456")
    public ResponseEntity<String> hello(@PathParam("accountId") String accountId) {
        return ResponseEntity.ok("Hello World!");
    }
}
