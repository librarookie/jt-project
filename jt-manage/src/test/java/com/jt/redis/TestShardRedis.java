package com.jt.redis;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
/**
 * 测试哨兵
 * @author Administrator
 *
 */
public class TestShardRedis {

	@Test
	public void test01() {
		List<JedisShardInfo> shards = new ArrayList<>();
		shards.add(new JedisShardInfo("192.168.220.136",6379));
		shards.add(new JedisShardInfo("192.168.220.136",6380));
		shards.add(new JedisShardInfo("192.168.220.136",6381));
		
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(500);	//最大池容量
		poolConfig.setMaxIdle(20); 	//设定最大空闲链接
		
		//实现连接池的操作
		ShardedJedisPool pool = new ShardedJedisPool(poolConfig, shards);
		
		//ShardedJedis jedis = new ShardedJedis(shards);
		ShardedJedis jedis = pool.getResource();
		jedis.set("1807", "学习redis分片");
		System.out.println("获取数据："+jedis.get("1807"));
		
		//jedis.close();		//将链接直接关闭
		pool.returnResource(jedis);	//将链接还回池中 	
		
	}
}
