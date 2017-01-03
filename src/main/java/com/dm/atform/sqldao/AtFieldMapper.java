package com.dm.atform.sqldao;

import java.util.List;
import java.util.Map;

import com.dm.atform.model.AtField;

public interface AtFieldMapper {
    int deleteByPrimaryKey(String id);

    int insert(AtField record);

    int insertSelective(AtField record);

    AtField selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AtField record);

    int updateByPrimaryKey(AtField record);

	List<AtField> selectByArgMap(Map map);
}