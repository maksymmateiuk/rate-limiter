package com.mmateiuk.ratelimiter.configuration;

import com.mmateiuk.ratelimiter.interceptor.RateLimitInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RateLimitRegistryConfig implements WebMvcConfigurer {

    private final RateLimitInterceptor rateLimitInterceptor;

    public RateLimitRegistryConfig(RateLimitInterceptor rateLimitInterceptor) {
        this.rateLimitInterceptor = rateLimitInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/api/v2/**");
    }
}
