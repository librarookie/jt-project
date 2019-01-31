package com.jt.web.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.service.HttpClientService;
import com.jt.web.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private HttpClientService httpClientService;

	@Override
	public Item findItemById(Long id) {
		Item item = null;
		
		String url = "http://manage.jt.com/web/item/findItemById/"+id;
		// 获取后台返回的Json 数据
		String result = httpClientService.doGet(url);
		try {
			item = objectMapper.readValue(result, Item.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return item;
	}

	@Override
	public ItemDesc findItemDescById(Long id) {
		ItemDesc itemDesc = null;
		
		String url = "http://manage.jt.com/web/item/findItemDescById/"+id;
		//itemDesc的 JSON 串
		String result = httpClientService.doGet(url);
		try {
			itemDesc = objectMapper.readValue(result, ItemDesc.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

}
