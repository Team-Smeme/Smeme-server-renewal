package com.smeem.output.cache.redis.adapter;

import com.smeem.application.port.output.cache.CachePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

//TODO: Redis 죽으면 어떻게 할 것인가?
@Configuration
@RequiredArgsConstructor
public class RedisCacheAdapter implements CachePort {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisTemplate<String, Integer> integerRedisTemplate;

    @Override
    public void setBit(String key, long offset, boolean value) {
        redisTemplate.opsForValue().setBit(key, offset, value);
    }

    @Override
    public boolean getBit(String key, long offset) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().getBit(key, offset));
    }

    @Override
    public Optional<Integer> getInt(String key) {
        return Optional.ofNullable(integerRedisTemplate.opsForValue().get(key));
    }

    @Override
    public void setInt(String key, int value) {
        integerRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void incrementInt(String key) {
        redisTemplate.opsForValue().increment(key);
    }
}
