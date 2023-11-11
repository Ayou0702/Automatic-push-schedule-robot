package com.enterprise.util;

import com.enterprise.common.handler.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 负责认证部分的工具类
 *
 * @author PrefersMin
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class AuthenticationUtil {

    /**
     * 验证码过期时间（单位：秒）
     */
    private static final long EXPIRATION_TIME = 60;

    private static final String REDIS_KEY_PREFIX = "sms_code:";

    private final RedisTemplate<String, String> redisTemplate;

    public Result generateSmsCode(String phoneNumber) {

        // 检查Redis中是否已存在该手机号的验证码
        String redisKey = REDIS_KEY_PREFIX + phoneNumber;
        String storedCode = redisTemplate.opsForValue().get(redisKey);

        if (storedCode != null) {
            // 检查距离上次生成是否已经过去一分钟
            long lastGenerationTime = Long.parseLong(storedCode.split(":")[1]);
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastGenerationTime < 60000) {
                String info = phoneNumber+"的验证码请求过于频繁，请稍后再试";
                LogUtil.error(info);
                return Result.failed().message("请求失败").description(info);
            }
        }

        // 生成验证码
        String code = generateCode();
        String codeWithTime = code + ":" + System.currentTimeMillis();

        LogUtil.info(phoneNumber+"的验证码是："+code);

        // 存入Redis并设置过期时间
        redisTemplate.opsForValue().set(redisKey, codeWithTime, EXPIRATION_TIME, TimeUnit.SECONDS);

        return Result.success().message("请求成功").description("验证码下发成功，请注意查收");

    }

    public Result verifySmsCode(String phoneNumber, String code) {

        // 获取Redis中存储的验证码
        String redisKey = REDIS_KEY_PREFIX + phoneNumber;
        String storedCodeWithTime = redisTemplate.opsForValue().get(redisKey);

        if (storedCodeWithTime == null) {
            return Result.failed().message("验证码不存在");
        }

        String storedCode = storedCodeWithTime.split(":")[0];
        long createTime = Long.parseLong(storedCodeWithTime.split(":")[1]);

        if (!storedCode.equals(code)) {
            return Result.failed().message("验证码不正确");
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - createTime > 5 * 60 * 1000) {
            // 验证码已过期，从Redis中删除验证码
            redisTemplate.delete(redisKey);
            return Result.failed().message("验证码已过期");
        }

        // 验证码验证成功后，从Redis中删除验证码
        redisTemplate.delete(redisKey);

        return Result.success();

    }

    private String generateCode() {

        // 生成六位随机数字验证码
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;

        return String.valueOf(code);

    }

    public boolean isValidPhoneNumber(String phone) {
        // 验证手机号是否符合条件，以 1 开头，长度为11位
        return phone != null && phone.matches("^1[0-9]{10}$");
    }

}
