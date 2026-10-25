package com.example.ticketbooking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisCacheService {
    private final StringRedisTemplate redisTemplate;

    public String get(String cacheKey) {
        return redisTemplate.opsForValue().get(cacheKey);
    }

    public void set(String cacheKey, String value, Duration ttl) {
        redisTemplate.opsForValue().set(cacheKey, value, ttl);
    }

    public void evict(String key) {
        redisTemplate.delete(key);
    }
}
