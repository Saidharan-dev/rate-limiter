package com.saidharan.rate_limiter.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

@Service
public class RateLimiterService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private DefaultRedisScript<Long> rateLimiterScript;

    private static final int WINDOW_SECONDS = 60;
    private static final int MAX_REQUESTS = 10;

    public boolean isAllowed(String ip) {

        String key = "rate_limit:" + ip;

        Long result = redisTemplate.execute(
                rateLimiterScript,
                Collections.singletonList(key),
                String.valueOf(WINDOW_SECONDS),
                String.valueOf(MAX_REQUESTS)
        );

System.out.println("IP = " + ip);
System.out.println("Redis Key = " + key);
System.out.println("Lua Result = " + result);

        return result != null && result == 1;
    }
}