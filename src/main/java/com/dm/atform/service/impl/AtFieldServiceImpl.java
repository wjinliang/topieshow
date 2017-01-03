package com.dm.atform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dm.atform.model.AtField;
import com.dm.atform.service.AtFieldService;
import com.dm.atform.sqldao.AtFieldMapper;
import com.dm.platform.util.UUIDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class AtFieldServiceImpl implements AtFieldService{
	@Autowired
	private AtFieldMapper fieldMapper;
	@Override
	public PageInfo<AtField> findByArg(Map map) {
		PageHelper.startPage(map);
		List<AtField> list = this.fieldMapper.selectByArgMap(map);
		PageInfo page = new PageInfo(list);
		return page;
	}

	@Override
	public List<AtField> findAll(Map map) {
		map.put("sort", "a_seq ");
		List<AtField> list = this.fieldMapper.selectByArgMap(map);
		return list;
	}
	@Override
	public AtField findOne(String id) {
		return this.fieldMapper.selectByPrimaryKey(id);
	}
	@Override
	public void saveOrUpdate(AtField record) {
		if(StringUtils.hasText(record.getId())){
			this.fieldMapper.updateByPrimaryKey(record);
		}else{
			record.setId(UUIDUtils.getUUID8());
			this.fieldMapper.insert(record);
		}
		
	}
	@Override
	public void delete(String id) {
		this.fieldMapper.deleteByPrimaryKey(id);
	}

	
	
}
