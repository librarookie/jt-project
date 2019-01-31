package com.jt.order.service;

import com.jt.common.po.Order;

public interface OrderService {

	String saveOrder(Order order);

	Order findOrderById(String orderId);

}
