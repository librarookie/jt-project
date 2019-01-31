package com.jt.manage.factory;

import java.util.Calendar;

import org.springframework.beans.factory.FactoryBean;

public class SpringFactory implements FactoryBean<Calendar> {

	//当程序加载该类时，容器内部会自动的调用getObject方法
	@Override
	public Calendar getObject() throws Exception {
		return Calendar.getInstance();
	}

	@Override
	public Class<?> getObjectType() {
		return Calendar.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

}
