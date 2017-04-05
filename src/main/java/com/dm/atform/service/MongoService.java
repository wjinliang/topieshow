package com.dm.atform.service;

import java.util.List;
import java.util.Map;

import com.dm.atform.model.AtTable;
import com.github.pagehelper.PageInfo;
import com.mongodb.DBObject;

public interface MongoService {

	PageInfo queryMulti(AtTable atTable, Integer pageNum,
			Integer pageSize, Map<String,Object> searchMap, String sort);

	PageInfo queryMulti1(AtTable atTable, Integer pageNum,
			Integer pageSize, Map<String,Object> searchMap, String sort);
	
	PageInfo queryMulti2(AtTable atTable, Integer pageNum,
			Integer pageSize, String fileds, Map<String,Object> searchMap, String sort);

	Object findOne(AtTable atTable, String id);

	void saveOrUpdate(AtTable atTable, Map record);

	void delete(AtTable atTable, String id);

	Object loadCollections();

	List<Map> findOne(AtTable atTable);
	
	long getCount(AtTable atTable,DBObject query);

	long getCount(AtTable atTable);
}
