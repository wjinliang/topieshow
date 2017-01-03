package com.dm.atform.service;

import java.util.List;
import java.util.Map;

import com.dm.atform.model.AtField;
import com.github.pagehelper.PageInfo;

public interface AtFieldService {

	PageInfo<AtField> findByArg(Map map);
	
	List<AtField> findAll(Map map);

	Object findOne(String id);

	void saveOrUpdate(AtField atField);

	void delete(String id);

}
