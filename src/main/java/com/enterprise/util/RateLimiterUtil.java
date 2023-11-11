package com.enterprise.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 负责IP限流的工具类
 *
 * @author PrefersMin
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class RateLimiterUtil {

    /**
     * 单次请求间隔时间（单位：秒）
     */
    private static final long EXPIRATION_TIME = 60;

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 检查IP在一分钟内的请求次数是否超过限制
     * @param ip 请求IP
     * @return true表示超过限制，false表示未超过限制
     */
    public boolean isRequestExceededLimit(String ip) {

        String redisKey = "request:" + ip;
        String storedCode = redisTemplate.opsForValue().get(redisKey);

        if (storedCode != null) {
            // 检查距离上次请求是否已经过去一分钟
            long lastRequestTime = Long.parseLong(storedCode.split("\\|")[1]);
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastRequestTime < 60000) {
                return true; // 返回true表示请求次数超过限制
            }
        }

        String RequestTime = redisKey + "|" + System.currentTimeMillis();
        // 存入Redis并设置过期时间
        redisTemplate.opsForValue().set(redisKey, RequestTime, EXPIRATION_TIME, TimeUnit.SECONDS);

        return false; // 返回false表示请求次数未超过限制

    }


}
