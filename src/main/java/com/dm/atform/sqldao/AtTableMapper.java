package com.dm.atform.sqldao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dm.atform.model.AtTable;
import com.dm.cms.model.TreeNode;

public interface AtTableMapper {
    int deleteByPrimaryKey(String id);

    int insert(AtTable record);

    int insertSelective(AtTable record);

    AtTable selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AtTable record);

    int updateByPrimaryKey(AtTable record);

	List<AtTable> selectByArgMap(Map map);

	List<TreeNode> selectTreeNode(@Param("pId")String pid,@Param("status") String status);

	Integer selectMaxSeq();
}