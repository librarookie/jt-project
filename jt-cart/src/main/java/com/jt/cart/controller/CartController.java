package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.cart.service.CartService;
import com.jt.common.po.Cart;
import com.jt.common.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private CartService cartService;
	
	//根据userId查询用户购物信息
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult findCartByUserId(@PathVariable Long userId){
		try {
			List<Cart> cartList = 
					cartService.findCartByUserId(userId);
			return SysResult.oK(cartList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"查询购物车失败");
	}
					//update/num/"+userId+"/"+itemId+"/"+num;
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(
			@PathVariable Long userId,
			@PathVariable Long itemId,
			@PathVariable Integer num) {
		try {
			cartService.updateCartNum(userId,itemId,num);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品数量修改失败");
	}
	
	//实现购物车新增
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCart(String cartJSON) {
		try {
			Cart cart = objectMapper.readValue(cartJSON, Cart.class);
			cartService.saveCart(cart);	//实现购物车入库
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "新增购物车失败");
	}
}
