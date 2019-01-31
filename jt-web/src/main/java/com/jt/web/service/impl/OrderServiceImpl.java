package com.jt.web.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Order;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.service.OrderService;
@Service
public class OrderServiceImpl implements OrderService {
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private HttpClientService httpClient;
	

	@Override
	public String saveOrder(Order order) {
		String url = "http://order.jt.com/order/create";
		String orderId = null;
		try {
			String orderJSON = objectMapper.writeValueAsString(order);
			Map<String,String> params = new HashMap<>();
			params.put("orderJSON", orderJSON);
			//获取远程返回的json数据
			String resultJSON = httpClient.doPost(url, params);
			SysResult sysResult = 
					objectMapper.readValue(resultJSON, SysResult.class);
			if(sysResult.getStatus() == 200) {
				orderId = (String) sysResult.getData();
			}
		} catch (Exception e) {
			System.out.println("前台传输数据或者解析数据异常"+e.getMessage());
			throw new RuntimeException();
		}
		return orderId;
	}


	@Override
	public Order findOrderById(String id) {
		String url = "http://order.jt.com/order/query/"+id;
		String orderJSON = httpClient.doGet(url);
		Order order = null;
		try {
			order = objectMapper.readValue(orderJSON, Order.class);
		} catch (IOException e) {
			System.out.println("获取order对象异常："+e.getMessage());
		}
		return order;
	}

	
}
