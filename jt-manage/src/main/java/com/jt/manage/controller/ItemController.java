package com.jt.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	/**
	 * http://localhost:8091/item/query?page=1&rows=20
	 * 返回JSON格式要求:
	 * 	total:返回记录总数
	 * 	rows: 返回的列表信息
	 */
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult findItemByPage(Integer page,Integer rows) {
		return itemService.findItemByPage(page,rows);
	}
	
	/**
	 * 根据商品分类的Id查询名称
	 * @ResponseBody进行数据解析时,如果解析的是对象(pojo/vo/List/Map..)
	 * 默认格式都采用utf-8格式解析.
	 * 如果解析的数据是String类型,则按照iso-8859-1格式进行解析.
	 * public class StringHttpMessageConverter extends AbstractHttpMessageConverter<String> {
	   public static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");
	
	   public abstract class AbstractJackson2HttpMessageConverter extends AbstractHttpMessageConverter<Object>
	   public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/cat/queryItemName",produces="text/html;charset=utf-8")
	@ResponseBody
	public String findItemCatNameById(Long itemId) {
		//response.setContentType("text/html;charset=utf-8");
		return itemService.findItemCatNameById(itemId);
	}
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDescById(@PathVariable("itemId")Long itemId) {
		try {
			ItemDesc itemDesc = itemService.findItemDescById(itemId);
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品详情查询失败");
	}
	
	//实现商品的新增
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item, String desc) {
		try {
			itemService.saveItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品新增失败");
	}
	
	//实现商品信息的修改
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item, String desc) {
		try {
			itemService.updateItem(item, desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品更新失败");
	}
	//实现商品的上架
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelf(String[] ids) {
		try {
			int status = 1;  	//1正常，2下架，3删除
			itemService.updateStatus(ids, status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "上架失败");
	}
	//实现商品的下架
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instock(String[] ids) {
		try {
			int status = 2;
			itemService.updateStatus(ids, status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "下架失败");
	}
	//实现商品信息的删除
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItems(String[] ids) {
		try {
			itemService.deleteItems(ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品删除失败");
	}
}
