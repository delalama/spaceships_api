package com.w2m.spaceships_api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

@Slf4j
public class CacheAspect {

    private final CacheManager cacheManager;

    public CacheAspect(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Pointcut("@annotation(org.springframework.cache.annotation.Cacheable)")
    public void cacheableMethods() {}

    @Around("cacheableMethods()")
    public Object checkCache(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        String cacheKey = generateCacheKey(methodName, args);

        Cache cache = cacheManager.getCache("spaceships");
        if (cache != null && cache.get(cacheKey) != null) {
            log.info("Cache hit for key: " + cacheKey);
        } else {
            log.info("Cache miss for key: " + cacheKey);
        }

        return joinPoint.proceed();
    }

    private String generateCacheKey(String methodName, Object[] args) {
        return methodName + ":" + args[0];
    }
}
