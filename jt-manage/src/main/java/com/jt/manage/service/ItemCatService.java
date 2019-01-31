package com.jt.manage.service;

import java.util.List;

import com.jt.manage.vo.EasyUITree;

public interface ItemCatService {

	List<EasyUITree> findItemCatNameById(Long parentId);

	List<EasyUITree> findCacheItemCat(Long parentId);

}
