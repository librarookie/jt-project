package com.jt.common.factory;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class JedisClusterFactory implements FactoryBean<JedisCluster> {

	private Resource propertySource;	//导入配置文件，加载节点信息
	private JedisPoolConfig poolConfig;	//注入池配置文件
	private String redisNodePrefix;		//定义节点信息
	
	
	public Set<HostAndPort> getNodes() {
		Set<HostAndPort> nodes = new HashSet<>();
		Properties pro = new Properties();
		try {
			pro.load(propertySource.getInputStream());
			
			//获取节点redis信息
			for(Object key : pro.keySet()) {
				//判断哪些是redis节点
				String strKey = (String) key;
				if(strKey.startsWith(redisNodePrefix)) {	//判断pro中的key
					//IP : 端口
					String value = pro.getProperty(strKey);
					String[] args = value.split(":");
					
					HostAndPort hostAndPort = 
							new HostAndPort(args[0], Integer.parseInt(args[1]));
					nodes.add(hostAndPort);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodes;
	}
	
	//通过工厂对象，创建jedisCluster对象
	@Override
	public JedisCluster getObject() throws Exception {
		Set<HostAndPort> nodes = getNodes();
		
		return new JedisCluster(nodes, poolConfig);
	}
	@Override
	public Class<?> getObjectType() {
		return JedisCluster.class;
	}
	@Override
	public boolean isSingleton() {
		return false;
	}


	public Resource getPropertySource() {
		return propertySource;
	}

	public void setPropertySource(Resource propertySource) {
		this.propertySource = propertySource;
	}

	public JedisPoolConfig getPoolConfig() {
		return poolConfig;
	}

	public void setPoolConfig(JedisPoolConfig poolConfig) {
		this.poolConfig = poolConfig;
	}

	public String getRedisNodePrefix() {
		return redisNodePrefix;
	}

	public void setRedisNodePrefix(String redisNodePrefix) {
		this.redisNodePrefix = redisNodePrefix;
	}

}
