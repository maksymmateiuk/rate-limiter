package com.mmateiuk.ratelimiter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitReachedException extends RuntimeException {

    private static String DEFAULT_LIMIT_REACHED_MESSAGE = "Rate limit has been reached.";

    public RateLimitReachedException() {
        super(DEFAULT_LIMIT_REACHED_MESSAGE);
    }

    public RateLimitReachedException(long reachedTime) {
        super(DEFAULT_LIMIT_REACHED_MESSAGE + "Try again it " + reachedTime);
    }
}
