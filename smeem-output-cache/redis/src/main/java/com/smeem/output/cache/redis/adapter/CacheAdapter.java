package com.smeem.output.cache.redis.adapter;

import com.smeem.application.port.output.cache.CachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@RequiredArgsConstructor
public class CacheAdapter implements CachePort {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void setBit(String key, long offset, boolean value) {
        redisTemplate.opsForValue().setBit(key, offset, value);
    }

    @Override
    public boolean getBit(String key, long offset) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getBit(key, offset));
    }
}
