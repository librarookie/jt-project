package com.jt.sso.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.User;
import com.jt.sso.mapper.UserMapper;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService {
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedisCluster;
	
	/**
	 * 业务分析：
	 * 根据传递的类型（type）
	 * 		1.查询username	
	 * 		2.查询phone
	 * 		3.查询email
	 * 校验时，只查询数据的记录总数，
	 * 		如果总数 = 0，证明后台没有该信息，可以使用	false
	 * 		如果总数 > 0，证明后台数据有数据，不能使用	true
	 * 
	 */
	@Override
	public boolean findCheckUser(String param, Integer type) {
		// 定义 User对象
		User user = new User();
		switch (type) {
		case 1:
			user.setUsername(param); break;
		case 2:
			user.setPhone(param); break;
		case 3:
			user.setPassword(param); break;
		}
		// 获取校验数据的记录总数
		int count = userMapper.selectCount(user);
		
		return count==0 ? false : true;
	}
	
	@Override
	public void saveUser(User user) {
		//将数据补全
		user.setEmail(user.getPhone());  //暂时用电话号码代替
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insert(user);
	}

	@Override
	public String findUserByUP(User user) {
		//根据用户名和密码进行查询
		List<User> userList = userMapper.select(user);
		if(userList.size() == 0) {
			return null;	//表示用户没有获取
		}
		User userDB = userList.get(0);
		String token = DigestUtils.md5Hex("JT_TICKET_"
					+System.currentTimeMillis()+user.getUsername());
		try {
			String userJSON = objectMapper.writeValueAsString(userDB);
			jedisCluster.setex(token, 3600*24*7, userJSON);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return token;
	}

	
}
