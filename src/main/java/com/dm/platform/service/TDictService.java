package com.dm.platform.service;

import java.util.List;
import java.util.Map;

import com.dm.platform.model.TDict;
import com.dm.platform.model.TDictItem;
import com.github.pagehelper.PageInfo;

public interface TDictService {

	List<TDictItem> selectByDictId(Integer dictId);

	TDict selectByPrimaryKey(Integer dictId);

	PageInfo<TDict> findDictByPage(Integer pageNum, Integer pageSize, Map map);

	Object insertOrUpdate(TDict tDict);

	Map deleteDict(int id);

	void insertOrUpdateItem(TDictItem iDictItem);

	TDictItem selectItemByKey(Integer itemid);

	void deleteItem(Integer itemid);

	void setSeq(Integer currentid, Integer targetid, Integer dictid);

	List<TDict> listTDictByTDictCode(String dictCode);

}
