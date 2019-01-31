package com.jt.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestRedisCluster {

	/**
	 * 1.创建集群操作对象
	 * 2.将集群操作的节点存入Set集合中
	 */
	@Test
	public void testRedisCluster() {
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.220.136",7000));
		nodes.add(new HostAndPort("192.168.220.136",7001));
		nodes.add(new HostAndPort("192.168.220.136",7002));
		nodes.add(new HostAndPort("192.168.220.136",7003));
		nodes.add(new HostAndPort("192.168.220.136",7004));
		nodes.add(new HostAndPort("192.168.220.136",7005));
		nodes.add(new HostAndPort("192.168.220.136",7006));
		nodes.add(new HostAndPort("192.168.220.136",7007));
		nodes.add(new HostAndPort("192.168.220.136",7008));
		
		JedisCluster jedisCluster = new JedisCluster(nodes);
		
		jedisCluster.set("1807", "集群测试成功！！");
		System.out.println(jedisCluster.get("1807"));
		
	}
}
