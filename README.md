# Rate limiter using Bucket4j
Bucket4j uses bucket-tokens algorithms.
Basic properties are:
- capacity
- tokens
- duration

## SimpleRateLimitController.class
Directly use limit inside controller.
- create Bucket bucket;
- add limit for it;
```
bucket.tryConsume(1) - checking is token avalaible for request, if not, return too many request.
```

## ByAccountRateLimitController.class
Here is some small logic to resolve what kind of limits need to apply by account. Then against checking tokens and returning too many requests if we exceed the limit.

## ByMvcInterceptorRateLimitController.class
Using mvc interceptor.
Creating RateLimitInterceptor where we have the same logic by account as in ByAccountRateLimitController.class.
Adding interceptor to registry in RateLimitRegistryConfig.class.
In RateLimitRegistryConfig.class we can control the rate limiting by:
```
.addPathPatterns(String pattern);
.addPathPatterns(List<String> patterns)
```

## ByAnnotationRateLimitController.class
### Annotation @RateLimited and RateLimitedAspect.class
Controlling rate limit by adding annotation. The same logic by account and default limits. 
