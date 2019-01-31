package com.jt.manage.service;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;

public interface ItemService {
	
	EasyUIResult findItemByPage(Integer page,Integer rows);

	String findItemCatNameById(Long itemId);
	/**
	 * 新增商品
	 * @param item
	 * @param desc 
	 */
	void saveItem(Item item, String desc);
	/**
	 * 修改商品信息
	 * @param item
	 */	
	void updateItem(Item item, String desc);
	/**
	 * 上架/下架商品更新
	 * @param ids
	 * @param status
	 */
	void updateStatus(String[] ids, int status);
	/**
	 * 删除商品信息
	 * @param ids
	 * @param status
	 */
	void deleteItems(String[] ids);

	ItemDesc findItemDescById(Long itemId);

	Item findItemByItemId(Long itemId);


}