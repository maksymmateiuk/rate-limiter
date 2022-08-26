package com.mmateiuk.ratelimiter.controllers;

import javax.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ByMvcInterceptorRateLimitController {

    @GetMapping("/api/v2/{accountId}/hello")
    public ResponseEntity<String> hello(@PathParam("accountId") String accountId) {
        return ResponseEntity.ok("Hello World!");
    }
}
