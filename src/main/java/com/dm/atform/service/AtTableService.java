package com.dm.atform.service;

import java.util.List;
import java.util.Map;

import com.dm.atform.model.AtTable;
import com.dm.cms.model.TreeNode;
import com.github.pagehelper.PageInfo;

public interface AtTableService {

	PageInfo<AtTable> findByArg(Map map);

	AtTable findOne(String id);

	void saveOrUpdate(AtTable atTable);

	void delete(String id);

	List<TreeNode> getTree(String tableId,String status);

	
}
