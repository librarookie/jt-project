package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.User;
import com.jt.common.vo.SysResult;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	
	//实现页面通用跳转
	@RequestMapping("/{moduleName}")
	public String toModole(
			@PathVariable("moduleName") String module) {
		return module;
	}
	
	//实现注册操作
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user){
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户新增失败");
	}
	
	//实现登陆操作
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult login(User user,HttpServletResponse response) {
		try {
			//根据用户名和密码实现校验
			String token = userService.findUserByUP(user);
			//判断token数据是否为null
			if(StringUtils.isEmpty(token)){
				throw new RuntimeException();
			}
			//token数据不为null,则将数据保存到Cookie中
			Cookie cookie = new Cookie("JT_TICKET",token);
			cookie.setPath("/");	//表示cookie的权限
			cookie.setMaxAge(3600 * 24 * 7);//保存7天
			response.addCookie(cookie);
			
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户登陆失败");
	}
	
	//实现登出操作
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {
		//1.删除redis缓存 	key=value
		String token = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals("JT_TICKET")) {
				token = cookie.getValue();
				break;
			}
		}
		jedisCluster.del(token);
		
		//新建cookie,删除cookie
		Cookie cookie = new Cookie("JT_TICKET","");
		cookie.setMaxAge(0); 	//cookie 0：表示立即删除
		cookie.setPath("/");
		response.addCookie(cookie);
		
		//3.重定向到首页
		return "redirect:/index.html";
	}
}
