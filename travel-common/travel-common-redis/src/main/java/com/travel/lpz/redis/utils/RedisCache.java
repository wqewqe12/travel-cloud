package com.travel.lpz.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@Component
public class RedisCache {
    @Autowired
    public RedisTemplate redisTemplate;

    public RedisCache(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long increment(String key , int incr){
        return redisTemplate.opsForValue().increment(key,incr);
    }

    /*
    * 缓存基本的对象，Integet,String等等
    * key缓存的键值
    * value缓存的值
    */
    public <T> void setCacheObject (final String key ,  final T value){
         redisTemplate.opsForValue().set(key,value);
    }
    /*
     * 缓存基本的对象，Integet,String等等
     * key缓存的键值
     * value缓存的值
     * timeout时间
     * timeUnit时间颗粒度
     */
    public <T> void setCacheObject (final String key , final T value , final Integer timeout , final TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value , timeout ,timeUnit);
    }
    /*设置有效时间
    * key Redis键
    * timeout超时时间
    * true=设置成功 false=设置失败
    * */

    public boolean expire(final String key , final long timeout ){
        return expire(key,timeout,TimeUnit.SECONDS);
    }
    /*设置有效时间
     * key Redis键
     * timeout超时时间
     * true=设置成功 false=设置失败
     * */
    public boolean expire(final String key , final Long timeout ,final TimeUnit unit){
        return expire(key,timeout,unit);
    }
    /*获取有效时间*/
    public long getExpire(final String key){
        return redisTemplate.getExpire(key);
    }
    /*判断key是否存在*/
    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }
    /*获取缓存的基本对象*/
    public <T> T getCacheObject(final String key){
        ValueOperations<String , T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /*删除单个对象*/
    public boolean deleteObjcet(final String key){
        return redisTemplate.delete(key);
    }
    /*删除集合对象*/
    public boolean deleteObjcet(final Collection collection){
        return redisTemplate.delete(collection) > 0;
    }
    /*缓存List数据*/
    public <T> long setCacheList(final String key , final List<T> dataList){
        Long count = redisTemplate.opsForList().rightPushAll(key , dataList);
        return count == null ? 0 : count;
    }
    /*获取缓存的List对象
    * key缓存的键值
    * 返回缓存键值对应的数据*/
    public <T> List<T> getCacheList(final String key){
        return redisTemplate.opsForList().range(key,0,-1);
    }
    /*缓存Set*/
    public <T>BoundSetOperations<String , T> setCacheSet(final String key ,final Set<T> dataSet){
        BoundSetOperations<String ,T> setOperations = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while(it.hasNext()){
            setOperations.add(it.next());
        }
        return setOperations;
    }
    /*获取缓存的set对象*/
    public <T> Set<T> getCacheSet(final String key){
        return redisTemplate.opsForSet().members(key);
    }
    /*缓存Map*/
    public <T> void setCacheMap(final String key ,final Map<String , T> dataMap){
        if(dataMap!=null){
            redisTemplate.opsForHash().putAll(key,dataMap);
        }
    }
    /*获取缓存的Map对象*/
    public <T> Map<String ,T> getCacheMap(final String key){
        return redisTemplate.opsForHash().entries(key);
    }
    /*往hash里存入数据*/
    public <T> void setCacheMapValue(final String key , final String hKey , final T value){
        redisTemplate.opsForHash().put(key,hKey,value);
    }
    /*获取Hash中的数据*/
    public <T> T getCacheMapValue(final String key , final String hKey){
        HashOperations<String , String , T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key,hKey);
    }
    /*获取多个Hash中的数据
    * redis键
    * hash键集合
    * 返回集合对象
    * */
    public <T> List<T> getCacheMapValue(final String key , final Collection<Object> hKeys){
        return redisTemplate.opsForHash().multiGet(key,hKeys);
    }
    /*删除Hash中的某条数据*/
    public boolean deleteCacheMapValue(final String key ,final String hKey){
        return redisTemplate.opsForHash().delete(key,hKey) > 0;
    }
    /*获取缓存的基本对象列表*/
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }










}