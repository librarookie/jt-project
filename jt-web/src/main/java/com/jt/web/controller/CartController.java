package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Cart;
import com.jt.common.vo.SysResult;
import com.jt.web.service.CartService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	//1.实现购物车展现
	@RequestMapping("/show")
	public String show(Model model) {
		Long userId = UserThreadLocal.get().getId();
		List<Cart> cartList = cartService.findCartByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	//修改购物车的数量
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updataCartNum(
			@PathVariable Long itemId,
			@PathVariable Integer num) {
		try {
			Long userId = UserThreadLocal.get().getId();
			cartService.updateCartNum(userId,itemId,num);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车商品修改失败");
	}
	
	//加入购物车
	@RequestMapping("/add/{itemId}")
	public String saveCart(@PathVariable Long itemId,Cart cart) {
		Long userId = UserThreadLocal.get().getId();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartService.saveCart(cart);
		
		return "redirect:/cart/show.html"; //重定向到购物车展示
	}
}
