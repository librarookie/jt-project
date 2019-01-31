package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.EasyUITree;

@Controller
@RequestMapping("/item")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 实现商品分类目录展现
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/cat/list")
	@ResponseBody
	public List<EasyUITree> findItemCatListById(
			@RequestParam(value="id",defaultValue="0")Long parentId) {
		//查询以及商品分类标题
		return itemCatService.findCacheItemCat(parentId);
	}
}
