package com.dm.atform.service.impl;

import java.util.ArrayList;
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
	public PageInfo<AtTable> findByArg(Map map,String tableId) {
		if(tableId!=null){
			List<String> ids = new ArrayList<String>();
			ids.add(tableId);
			AtTable a = this.tableMapper.selectByPrimaryKey(tableId);
			map.put("pids", getChild(a,ids));
		}
		PageHelper.startPage(map);
		List<AtTable> list = this.tableMapper.selectByArgMap(map);
		PageInfo page = new PageInfo(list);
		return page;
	}
	//获取子分类
	private List<String> getChild(AtTable parent,List<String> ids) {
		if(parent.getType()!=null && parent.getType().equals("1")){//1分类 2 数据表
		List<AtTable> list = this.tableMapper.selectByPid(parent.getId());
		ids.add(parent.getId());
		for(AtTable a:list){
			getChild(a,ids);
		}
		}
		return ids;
	}
	@Override
	public List<AtTable> findAllByArg(Map map) {
		List<AtTable> list = this.tableMapper.selectByArgMap(map);
		return list;
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
		AtTable record = new AtTable();
		record.setId(id);
		record.setStatus("9");
		this.tableMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public List<TreeNode> getTree(Map map) {
		return this.tableMapper.selectTreeNode(map);
	}
	@Override
	public Long countByArg(Map map) {
		return this.tableMapper.countByArg(map);
	}
	@Override
	public List<AtTable> findByPid(String pid) {
		List<AtTable> list = this.tableMapper.selectByPid(pid);
		return list;
	}
	
	
}
