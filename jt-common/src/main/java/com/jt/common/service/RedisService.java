package com.jt.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;

@Service
public class RedisService {

	//有的工程需要，有的工程不需要。设置required=false，有就注入，没有就不注入。
    @Autowired(required = false)
    private JedisSentinelPool sentinelPool;
    //private ShardedJedisPool jedisPool;
    
    public String get(String key) {
    	Jedis jedis = sentinelPool.getResource();
    	String result = jedis.get(key);
    	sentinelPool.returnResource(jedis);
    	return result;
    }
    //为数据设定超时时间
    public void set(String key,String value,int seconds) {
    	Jedis jedis = sentinelPool.getResource();
    	jedis.setex(key, seconds ,value);
    	sentinelPool.returnResource(jedis);
    }
    public void set(String key,String value) {
    	Jedis jedis = sentinelPool.getResource();
    	jedis.set(key, value);
    	sentinelPool.returnResource(jedis);
    }
}
