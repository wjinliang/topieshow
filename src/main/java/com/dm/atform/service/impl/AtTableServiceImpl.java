package com.dm.atform.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dm.atform.model.AtTable;
import com.dm.atform.service.AtTableService;
import com.dm.atform.sqldao.AtTableMapper;
import com.dm.cms.model.TreeNode;
import com.dm.platform.util.UUIDUtils;
import com.dm.platform.util.UserAccountUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AtTableServiceImpl implements AtTableService{
	@Autowired
	private AtTableMapper tableMapper;
	@Override
	public PageInfo<AtTable> findByArg(Map map) {
		PageHelper.startPage(map);
		List<AtTable> list = this.tableMapper.selectByArgMap(map);
		PageInfo page = new PageInfo(list);
		return page;
	}
	@Override
	public AtTable findOne(String id) {
		return this.tableMapper.selectByPrimaryKey(id);
	}
	@Override
	public void saveOrUpdate(AtTable record) {
		if(StringUtils.hasText(record.getId())){
			this.tableMapper.updateByPrimaryKeySelective(record);
		}else{
			record.setSeq(1);
			Integer seq = this.tableMapper.selectMaxSeq();
			if(seq!=null) record.setSeq(seq+1);
			record.setId(UUIDUtils.getUUID8());
			record.setCreateDate(new Date());
			record.setCreateUser(UserAccountUtil.getInstance().getCurrentUser());
			this.tableMapper.insert(record);
		}
		
	}
	@Override
	public void delete(String id) {
		this.tableMapper.deleteByPrimaryKey(id);
	}
	@Override
	public List<TreeNode> getTree(String tableId,String status) {
		return this.tableMapper.selectTreeNode(tableId,status);
	}
	
	
}
