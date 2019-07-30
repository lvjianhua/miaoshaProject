package com.lv_miaoshaProject.service;

import java.util.List;
import java.util.Set;

/**
 * redis接口
 */
public interface RedisService {

    // 操作redis里的字符串类型
    public boolean set(String key, Object value);

    public Object get(String key);

    // 设置redis里一个键的存活时间
    public boolean expire(String key, long expire);

    // redis里面的List的操作
    public <T> boolean setList(String key, List<T> list);

    public <T> List<T> getList(String key, Class<T> clz);

    // redis里面的Map的操作
    public long lpush(String key, Object obj);

    public long rpush(String key, Object obj);

    // redis只支持取一次数据 取出就会被清除
    public String lpop(String key);

    // redis只支持取一次数据 取出就会被清除
    public <T> T getMyObject(String key, Class<T> clzs);

    // 检测该key值是否存在
    public boolean checkKey(String key);

    // 删除操作redis里的字符串类型
    public void del(String key);

    // redis操作hash
    public Boolean setHash(String rediskey, String key, Object value);

    // del 的hash属性
    public Long deleteHash(String rediskey, String key);

    // 检测是否存在这个hash中的某个属性
    public Boolean hasHashKey(String rediskey, String key);

    // 得到hash中的某个属性
    public <T> T getHashVal(String rediskey, String key, Class<T> clzs);

    // 获得hash中所有的Keys
    public Set<Object> getHashKeyList(String rediskey);

    // 获得hash中所有的values
    public <T> List<T> getHashValList(String rediskey, Class<T> clz);

    // 如果不存在就设置 如存在则不设置
    public boolean setNX(String key, Object value);
}  

