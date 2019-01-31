package com.jt.web.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Cart;
import com.jt.common.po.Order;
import com.jt.common.vo.SysResult;
import com.jt.web.service.CartService;
import com.jt.web.service.OrderService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/order")
public class OrderController {
	

	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;
	
	//实现订单页面的跳转
	@RequestMapping("/create")
	public String create(Model model) {
		Long userId = UserThreadLocal.get().getId();
		// 根据userId查询购物车信息
		List<Cart> cartList = cartService.findCartByUserId(userId);
		model.addAttribute("carts", cartList);
		return "order-cart";
	}
	
	//订单的新增 ，跳转返回orderId号
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order) {
		try {
			Long userId = UserThreadLocal.get().getId();
			order.setUserId(userId);
			String orderId = orderService.saveOrder(order);
			if(! StringUtils.isEmpty(orderId)) {
				return SysResult.oK(orderId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "新增订单失败");
	}
	
	@RequestMapping("/success")
	public String findOrderById(String id,Model model) {
		try {
			//根据id查询订单数据
			Order order = orderService.findOrderById(id);
			model.addAttribute("order", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
