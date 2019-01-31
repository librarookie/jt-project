package com.jt.manage.factory;

import java.util.Calendar;

/**
 * 静态工厂demo
 * @author Administrator
 *
 */
public class StaticFactory {

	//静态工厂必须有静态方法
	public static Calendar getIn() {
		return Calendar.getInstance();
	}
}
