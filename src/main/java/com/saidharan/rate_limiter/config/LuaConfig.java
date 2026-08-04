package com.saidharan.rate_limiter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
public class LuaConfig {

    @Bean
    public DefaultRedisScript<Long> rateLimiterScript() {

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();

        script.setLocation(new ClassPathResource("lua/ratelimiter.lua"));

        script.setResultType(Long.class);

        return script;
    }
}