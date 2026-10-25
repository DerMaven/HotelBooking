package com.example.ticketbooking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisLockService {
    private final StringRedisTemplate redisTemplate;
    private static final String LOCK_ROOM = "lock:room";


}
