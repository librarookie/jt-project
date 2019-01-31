package com.jt.web.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.User;
import com.jt.web.thread.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

/**
 * 定义用户拦截器
 * @author Administrator
 *
 */
public class UserInterceptor implements HandlerInterceptor {
	private ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private JedisCluster jedisCluster;
	
	/**
	 * 1.获取用户的cookie获取token数据
	 * 2.判断token中是否有数据
	 * 		false： 表示没有登陆，则重定向到用户登陆页面
	 * 		true： 表示用户之前登陆过，从redis中根据token获取userJSON，再次判断是否有数据
	 * 			false： 没有数据，则重定向到用户登陆页面
	 * 			true： 表示有数据，则程序予以放行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = null;
		//1.获取Cookie
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		//2.判断token是否有数据
		if(token != null) {
			//2.1 判断redis集群中是否有数据
			String userJSON = jedisCluster.get(token);
			if(userJSON != null) {
				//将user数据存入线程中
				User user = objectMapper.readValue(userJSON, User.class);
				UserThreadLocal.set(user);
				//证明用户已经登陆，放行
				return true;
			}
		}
		response.sendRedirect("/user/login.html");	//配置重定向
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//关闭threadLocal
		UserThreadLocal.remove();
	}

}
