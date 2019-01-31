package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.User;
import com.jt.common.vo.SysResult;
import com.jt.sso.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluseter;
	
	// 实现用户信息的校验
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public MappingJacksonValue findCheckUser(
			@PathVariable String param,
			@PathVariable Integer type,String callback) {
		// 校验用户信息是否存在		存在true，不存在false
		boolean flag = userService.findCheckUser(param, type);
		MappingJacksonValue value = 
				new MappingJacksonValue(SysResult.oK(flag));
		value.setJsonpFunction(callback);	//添加回调
		return value;
	}
	
	//编辑jt-sso后台业务,实现用户新增
	@RequestMapping("/register")
	@ResponseBody
	public SysResult saveUser(User user){
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"新增用户失败");
	}
	
	//通过用户名和密码实现登录
	@RequestMapping("/login")
	@ResponseBody
	public SysResult selectUser(User user) {
		try {
			String token = userService.findUserByUP(user);
			if(StringUtils.isEmpty(token)) {
				throw new RuntimeException();
			}
			return SysResult.oK(token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户登录失败");
	}
	
	//实现用户根据token查询用户信息
	@RequestMapping("/query/{token}")
	@ResponseBody
	public MappingJacksonValue findUserByToken(
			@PathVariable String token,String callback) {
		//获取redis中的json串
		String userJSON = jedisCluseter.get(token);
		MappingJacksonValue value = null;
		if(StringUtils.isEmpty(userJSON)) {
			value = new MappingJacksonValue(SysResult.build(201, "用户登录失败"));
		} else {
			value = new MappingJacksonValue(SysResult.oK(userJSON));
		}
		value.setJsonpFunction(callback);
		return value;
	}
}
