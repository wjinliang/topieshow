package com.dm.platform.sqldao;

import java.util.List;

import com.dm.platform.model.TDictItem;

public interface TDictItemMapper {
    int deleteByPrimaryKey(Integer itemId);

    int insert(TDictItem record);

    int insertSelective(TDictItem record);

    TDictItem selectByPrimaryKey(Integer itemId);

    int updateByPrimaryKeySelective(TDictItem record);

    int updateByPrimaryKey(TDictItem record);
    
    List<TDictItem> selectByDictId(Integer dictId);
}