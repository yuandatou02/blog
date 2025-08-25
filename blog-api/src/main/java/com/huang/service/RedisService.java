package com.huang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Redis服务接口实现类
 *
 * @author Ikaros
 * @since 2025/8/25 13:55 星期一
 */
@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 保存缓存对象
     *
     * @param key   缓存key
     * @param value 缓存对象
     * @param <T>   缓存对象类型
     */
    public <T> void setObject(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 保存缓存对象
     *
     * @param key   缓存key
     * @param clazz 缓存对象类型
     * @param <T>   缓存对象类型
     * @return 缓存对象
     */
    public <T> T getObject(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return clazz.cast(value);
    }

    /**
     * 删除缓存对象
     *
     * @param key 缓存key
     */
    public void deleteObject(String key) {
        redisTemplate.delete(key);
    }
}
