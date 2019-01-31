package com.jt.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
/**
 * 测试哨兵分片
 * @author Administrator
 *
 */
public class TestSentinel {

	@Test
	public void test01() {
		/**
		 * masterName: 主机的变量名称
		 */
		Set<String> sentinels = new HashSet<>();
		//sentinels.add(new HostAndPort("192.168.220.136",26379).toString());
		sentinels.add("192.168.220.136:26379");
		
		JedisSentinelPool pool = new JedisSentinelPool("mymaster", sentinels);
		
		Jedis jedis = pool.getResource();
		jedis.set("aa", "哨兵操作");
		System.out.println(jedis.get("aa"));
		pool.returnResource(jedis);
	}
}
