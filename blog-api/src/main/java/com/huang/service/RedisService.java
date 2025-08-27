package com.huang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

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
     * 获取缓存集合
     *
     * @param key 缓存key
     * @return 缓存集合
     */
    public Map<Object, Object> getHashAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 递增hash对象
     *
     * @param key     缓存key
     * @param hashKey hash key
     * @param delta   hash delta
     */
    public void incrHash(String key, String hashKey, Long delta) {
        redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * 获取缓存集合
     *
     * @param key    缓存key
     * @param values 缓存值集合
     * @param <T>    缓存值类型
     */
    public <T> void setSet(String key, Collection<T> values) {
        if (values != null && !values.isEmpty()) {
            redisTemplate.opsForSet().add(key, values.toArray());
        }
    }

    /**
     * 设置缓存集合
     *
     * @param key   缓存key
     * @param value 缓存值
     * @param <T>   缓存值类型
     */
    public <T> void setSet(String key, T value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 递减hash对象
     *
     * @param key     缓存key
     * @param hashKey hash key
     * @param delta   hash delta
     */
    public void decrHash(String key, String hashKey, Long delta) {
        redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    /**
     * 删除多个缓存
     *
     * @param key    缓存key
     * @param values 缓存值集合
     * @param <T>    缓存值类型
     * @return 删除的数量
     */
    public <T> Long deleteSet(String key, Collection<T> values) {
        if (values == null || values.isEmpty()) {
            return 0L;
        }
        return redisTemplate.opsForSet().remove(key, values.toArray());
    }

    /**
     * 删除单个缓存
     *
     * @param key   缓存key
     * @param value 缓存值
     * @param <T>   缓存值类型
     */
    public <T> void deleteSet(String key, T value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 设置缓存hash对象
     *
     * @param key   缓存key
     * @param value 缓存对象
     * @param <T>   缓存对象类型
     * @return 是否成功
     */
    public <T> Boolean hasSetValue(String key, T value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取缓存hash对象
     *
     * @param key     缓存key
     * @param hashKey hash key
     * @param clazz   缓存对象类型
     * @param <T>     缓存对象类型
     * @return 缓存对象
     */
    public <T> T getHash(String key, String hashKey, Class<T> clazz) {
        Object value = redisTemplate.opsForHash().get(key, hashKey);
        if (value == null) {
            return null;
        }
        return clazz.cast(value);
    }

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
