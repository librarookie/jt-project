package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jt.common.mapper.SysMapper;
import com.jt.common.po.Item;

public interface ItemMapper extends SysMapper<Item> {

	/*@Insert
	@Update
	@Delete*/
	/**
	 * 说明:因为不同的数据库对于大小写,有不同的要求.
	 * 操作系统中.window不区分大小写,但是在Linux系统中
	 * 严格区分大小写.所以为了保持一致统统都小写.
	 * ctrl + shift  + y 小写
	 * ctrl + shift  + x 大写
	 * @return
	 */
	@Select("select count(*) from tb_item")
	int findItemCount();

	/**
	 * Mybatis的Mapper接口不允许多值传输
	 * 思路:
	 * 	 将多值封装为单值
	 * 	1.将值封装到对象中pojo
	 * 	2.将值封装为集合  数组array/List集合/Map集合
	 * @param start
	 * @param rows
	 * @return
	 */
	List<Item> findItemByPage(@Param("start")int start, @Param("rows")Integer rows);
	
	@Select("select name from tb_item_cat where id=#{itemId}")
	String findItemCatNameById(Long itemId);
	
	void updateStatus(@Param("ids")String[] ids, @Param("status")int status);

}
