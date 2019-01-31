package com.jt.order.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Order;
import com.jt.common.vo.SysResult;
import com.jt.order.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	private ObjectMapper objectMapper = new ObjectMapper();
	

	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/create")
	@ResponseBody
	public SysResult saveOrder(String orderJSON) {
		try {
			Order order = objectMapper.readValue(orderJSON, Order.class);
			String orderId = orderService.saveOrder(order);
			if(! StringUtils.isEmpty(orderId)) {
				return SysResult.oK(orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "新增订单失败");
	}
	
	//根据orderId查询订单信息
	@RequestMapping("/query/{orderId}")
	@ResponseBody
	public Order findOrderById(@PathVariable String orderId) {
		Order order = orderService.findOrderById(orderId);
		return order;
	}
}
