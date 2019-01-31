package com.jt.redis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestRedis {

	//测试字符串	IP: 6379
	@Test
	public void testString() {
		Jedis jedis = new Jedis("192.168.220.136",6379);
		jedis.set("aa", "1807班");
		System.out.println(jedis.get("aa"));
		
	}
	//测试hash
	@Test
	public void testHash() {
		Jedis jedis = new Jedis("192.168.220.136",6379);
		jedis.hset("dog", "id", "100");
		jedis.hset("dog", "name", "中华田园犬");
		jedis.hset("dog", "age", "18");
		
		System.out.println(jedis.hget("dog", "name"));
		System.out.println(jedis.hgetAll("dog"));
	}
	//测试list集合
	@Test
	public void testList() {
		Jedis jedis = new Jedis("192.168.220.136",6379);
		jedis.lpush("idList", "1","2","3","4","5");
		System.out.println(jedis.lpop("isList"));	
	}
	//redis中事务控制
	@Test
	public void testTx() {
		Jedis jedis = new Jedis("192.168.220.136",6379);
		Transaction tran = jedis.multi();	//开启事务
		tran.set("aa", "aaaa");
		tran.set("bb", "bbbb");
		tran.exec();	//提交事务
		//tran.discard();		//回滚事务
		System.out.println(jedis.get("aa"));
	}
	
}
