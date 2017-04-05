package com.dm.atform.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.atform.model.SysDataCount;
import com.dm.atform.service.SysDataCountService;
import com.dm.atform.sqldao.SysDataCountMapper;

@Service
public class SysDataCountServiceImpl implements SysDataCountService {

	@Autowired
	private SysDataCountMapper mapper;
	@Override
	public Map getSysDataCount() {
		Map map = new HashMap();
		List<SysDataCount> list = this.mapper.selectListByArg(map);
		long full = 0l;
		long add = 0l;
		for(SysDataCount s:list){
			full+= s.getFullCount();
			add += s.getAddCount();
		}
		map.put("fullCount", full);
		map.put("addCount", add);
		return map;
	}

}
