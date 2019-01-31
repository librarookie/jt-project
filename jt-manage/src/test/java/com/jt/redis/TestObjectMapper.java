package com.jt.redis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.pojo.User;

public class TestObjectMapper {

	//将对象转化为 JSON 数据
	@Test
	public void objectToJSON() throws IOException {
		User user = new User();
		user.setId(1);
		user.setName("tom");
		user.setAge(18);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(user);
		System.out.println(json);
		
		//将 JSON 数据转化为对象
		User usr = mapper.readValue(json, User.class);
		System.out.println(usr);
	}
	@Test
	public void testJsonList() throws IOException {
		User user = new User();
		user.setId(1);
		user.setName("tom");
		user.setAge(18);
		user.setSex("男");
		
		User user1 = new User();
		user1.setId(1);
		user1.setName("tom");
		user1.setAge(18);
		user1.setSex("男");
		
		User user2 = new User();
		user2.setId(1);
		user2.setName("tom");
		user2.setAge(18);
		user2.setSex("男");
		

		List<User> list = new ArrayList<>();
		list.add(user);
		list.add(user1);
		list.add(user2);
		ObjectMapper mapper = new ObjectMapper();
		//将list对象转为 JSON 串
		String listJSON = mapper.writeValueAsString(list);
		System.out.println(listJSON);
		
		//将JSON 串转换为对象
		List<User> ulist = mapper.readValue(listJSON, list.getClass());
		System.out.println(ulist);
	}
}
