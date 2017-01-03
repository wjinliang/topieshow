package com.dm.platform.sqldao;

import java.util.List;
import java.util.Map;

import com.dm.platform.model.TDict;

public interface TDictMapper {
    int deleteByPrimaryKey(Integer dictId);

    int insert(TDict record);

    int insertSelective(TDict record);

    TDict selectByPrimaryKey(Integer dictId);

    int updateByPrimaryKeySelective(TDict record);

    int updateByPrimaryKey(TDict record);

	List<TDict> selectListByArg(Map map);

	List<TDict> findTDictByDictCode(String dictCode);
}