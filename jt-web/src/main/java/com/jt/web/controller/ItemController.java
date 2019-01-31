package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.web.service.ItemService;

@Controller
@RequestMapping("/items")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/{itemId}")
	public String findItemById(@PathVariable("itemId")Long id,Model model) {
		
		Item item = itemService.findItemById(id);
		model.addAttribute("item", item);
		
		// 实现商品详情展现
		ItemDesc itemDesc = itemService.findItemDescById(id);
		model.addAttribute("itemDesc", itemDesc);
		return "item";
	}
}
