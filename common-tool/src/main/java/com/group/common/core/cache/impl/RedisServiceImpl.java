package com.group.common.core.cache.impl;

import com.group.common.core.cache.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 作者：dailing
 * 生成日期：2018/8/13 下午5:55
 * 描述：
 */
@Service
public class RedisServiceImpl implements RedisService {


    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public void del(final String... keys) {
        if (keys.length==1){
            redisTemplate.delete(keys);
        }else if (keys.length>1){
            for (int i = 0; i < keys.length; i++) {
                redisTemplate.delete(keys[i]);
            }
        }
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long timeout,TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Long rightPushAll(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public Long rightPushAll(String key, Collection<Object> values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public List<Object> list(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public Object index(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    @Override
    public void set(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    @Override
    public Long remove(String key, Object value) {
        return redisTemplate.opsForList().remove(key, 0, value);
    }


}
