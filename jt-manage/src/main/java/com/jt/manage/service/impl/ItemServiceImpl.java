package com.jt.manage.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.po.Item;
import com.jt.common.po.ItemDesc;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public EasyUIResult findItemByPage(Integer page, Integer rows) {
		//1.查询商品的记录总数
//		int total = itemMapper.findItemCount();
		/**
		 * 关于通用Mapper用法：
		 * sql: select count(*) from tname id=100l and title="手机"
		 * Item item = new Item();
		 * item.setId(100L);
		 * item.setTitle("手机");
		 */
		int total = itemMapper.selectCount(null);
		/**
		 * 第一页  SELECT * FROM tb_item ORDER BY updated DESC LIMIT 0,10
		 * 第二页  SELECT * FROM tb_item ORDER BY updated DESC LIMIT 10,10
		 * 第三页  SELECT * FROM tb_item ORDER BY updated DESC LIMIT 20,10
		 * 第n页    SELECT * FROM tb_item ORDER BY updated DESC LIMIT (n-1)*rows,rows
		 */
		int start = (page - 1) * rows;	//起始位置
		List<Item> itemList = itemMapper.findItemByPage(start,rows);
		return new EasyUIResult(total, itemList);
	}

	@Override
	public String findItemCatNameById(Long itemId) {
		return itemMapper.findItemCatNameById(itemId);
	}

	@Override
	public void saveItem(Item item,String desc) {
		item.setStatus(1); 		//表示商品正常
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		//利用通用Mapper实现入库操作
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	public void updateItem(Item item, String desc) {
		item.setUpdated(new Date());
		//动态更新,更新其中不为空的数据
		itemMapper.updateByPrimaryKeySelective(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(item.getId());
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}

	@Override
	public void updateStatus(String[] ids, int status) {
		itemMapper.updateStatus(ids, status);
	}
	
	/**
	 * 先删除关联表记录，在删除主表记录
	 */
	@Override
	public void deleteItems(String[] ids) {
		itemDescMapper.deleteByIDS(ids);
		itemMapper.deleteByIDS(ids);
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		return itemDescMapper.selectByPrimaryKey(itemId);
	}

	@Override
	public Item findItemByItemId(Long itemId) {
		return itemMapper.selectByPrimaryKey(itemId);
	}

}
