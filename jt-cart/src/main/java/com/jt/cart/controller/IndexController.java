package com.jt.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.cart.service.CartService;

@Controller
@RequestMapping("/cart")
public class IndexController {

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/index")
	@ResponseBody
	public String index() {
		return "aaa";
	}
}
