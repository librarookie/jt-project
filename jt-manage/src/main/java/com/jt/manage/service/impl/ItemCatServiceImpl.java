package com.jt.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.ItemCat;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.EasyUITree;

import redis.clients.jedis.JedisCluster;
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;
	@Autowired
	private JedisCluster jedisCluster;
	//private RedisService redisService;
	//private jedis jedis;
	
	@Override
	public List<EasyUITree> findItemCatNameById(Long parentId) {
		//根据父级id查询分类信息
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		
		List<ItemCat> itemCatList = itemCatMapper.select(itemCat);
		List<EasyUITree> treeList = new ArrayList<>();
		
		for(ItemCat cat : itemCatList) {
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(cat.getId());
			easyUITree.setText(cat.getName());
			
			String state = cat.getIsParent() ? "closed" : "open";
			easyUITree.setState(state);
			treeList.add(easyUITree);
		}
		return treeList;
	}

	@Override
	public List<EasyUITree> findCacheItemCat(Long parentId) {
		List<EasyUITree> treeList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		//用户查询先查询缓存
		String key = "ITEM_CAT_"+parentId;
		String json = jedisCluster.get(key);
		try {
			if(StringUtils.isEmpty(json)) {
				//表示缓存中没有数据
				treeList = findItemCatNameById(parentId);
				//将数据保存到缓存中
				String listJSON = mapper.writeValueAsString(treeList);
				//将数据保存到redis中
				jedisCluster.set(key, listJSON);
				System.out.println("第一次查询数据库");
			} else {
				//表示缓存中有数据,直接转化为对象
				treeList = mapper.readValue(json, treeList.getClass());
				System.out.println("缓存查询成功!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return treeList;
	}
}
