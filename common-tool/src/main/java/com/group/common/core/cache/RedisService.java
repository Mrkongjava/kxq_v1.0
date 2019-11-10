package com.group.common.core.cache;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 版权：小月科技
 * 作者：dailing
 * 生成日期：2018/8/13 下午5:54
 * 描述：
 */
public interface RedisService {

    /**
     * 通过key删除
     *
     * @param keys
     */
    public void del(String... keys);

    /**
     * 设置key，value值，value可以为字符串，整数，浮点数
     * @param key
     * @param value
     */
    public void set(String key, Object value);

    /**
     * 设置key，value值，value可以为字符串，整数，浮点数，timeout为过期时间，单位秒
     * @param key
     * @param value
     * @param timeout
     */
    public void set(String key, Object value, long timeout, TimeUnit unit);

    /**
     * 根据key获取value值
     * @param key
     * @return
     */
    public Object get(String key);

    /**
     * 批量把一个数组插入到列表中
     * @param key
     * @param values
     * @return
     */
    public Long rightPushAll(String key, Object... values);

    /**
     * 批量把一个集合插入到列表中
     * @param key
     * @param values
     * @return
     */
    public Long rightPushAll(String key, Collection<Object> values);


    /**
     * 返回存储在键中的列表的指定元素。偏移开始和停止是基于零的索引，其中0是列表的第一个元素（列表的头部），1是下一个元素
     * 如果返回所有 start:0，end：-1
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> list(String key, long start, long end);

    /**
     * 根据下表获取列表中的值，下标是从0开始的
     * @param key
     * @param index
     * @return
     */
    public Object index(String key, long index);

    /**
     * 在列表中index的位置设置value值
     * @param key
     * @param index
     * @param value
     */
    public void set(String key, long index, Object value);

    /**
     * 删除值为value的元素
     * @param key
     * @param value
     * @return
     */
    public Long remove(String key, Object value);

}
