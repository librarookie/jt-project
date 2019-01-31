package com.jt.web.thread;

import com.jt.common.po.User;

public class UserThreadLocal {

	private static ThreadLocal<User> userThread = new ThreadLocal<>();
	
	public static void set(User user) {
		userThread.set(user);
	}
	public static User get() {
		return userThread.get();
	}
	
	//防止内存泄漏（内存泄漏问题）
	public static void remove() {
		userThread.remove();
	}
}
