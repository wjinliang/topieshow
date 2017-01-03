package com.dm.platform.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dm.platform.model.TDict;
import com.dm.platform.model.TDictItem;
import com.dm.platform.service.TDictService;
import com.dm.platform.sqldao.TDictItemMapper;
import com.dm.platform.sqldao.TDictMapper;
import com.dm.platform.util.ResponseUtil;
//import com.dm.platform.util.DmErrorException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("tDictServiceImpl")
public class TDictServiceImpl implements TDictService{
	
	@Autowired
	TDictItemMapper tDictItemMapper;
	
	@Autowired
	TDictMapper tDictMapper;
	
	@Override
	public List<TDictItem> selectByDictId(Integer dictId)
	{
		return tDictItemMapper.selectByDictId(dictId);
	}
	
	@Override
	public TDict selectByPrimaryKey(Integer dictId)
	{
		return tDictMapper.selectByPrimaryKey(dictId);
	}

	@Override
	public PageInfo<TDict> findDictByPage(Integer pageNum, Integer pageSize,
			Map map) {
		PageHelper.startPage(pageNum, pageSize);
		List<TDict> list = this.tDictMapper.selectListByArg(map);
		PageInfo<TDict> page = new PageInfo<TDict>(list);
		return page;
	}

	@Override
	public Object insertOrUpdate(TDict tDict) {
		if(tDict.getDictId()!=null)
			return this.updateDict(tDict);
		return this.insertDict(tDict);
		
	}

	private Object insertDict(TDict tDict) {
		if(this.listTDictByTDictCode(tDict.getDictCode()).size()>0){
			return ResponseUtil.error("字典码重复");
		}
		this.tDictMapper.insert(tDict);
		return ResponseUtil.success("操作成功！");
	}

	private Object updateDict(TDict tDict) {
		List<TDict> list = this.listTDictByTDictCode(tDict.getDictCode());
		if(list.size()>0){
			if(!list.get(0).getDictId().equals(tDict.getDictId())){
				return ResponseUtil.error("字典码重复");
			}
		}
		this.tDictMapper.updateByPrimaryKeySelective(tDict);
		return ResponseUtil.success("操作成功！");
		
	}

	@Override
	public Map deleteDict(int id) {
		List<TDictItem> list = this.tDictItemMapper.selectByDictId(id);
		if(list.size()>0)
			return ResponseUtil.error("请先删除该字典下的内容！");
		this.tDictMapper.deleteByPrimaryKey(id);
		return ResponseUtil.success("操作成功！");
	}

	@Override
	public void insertOrUpdateItem(TDictItem iDictItem) {
		if(iDictItem.getItemId()==null){
			this.tDictItemMapper.insertSelective(iDictItem);
			iDictItem.setItemSeq(iDictItem.getItemId().longValue());
		}
		this.tDictItemMapper.updateByPrimaryKeySelective(iDictItem);
		
	}

	@Override
	public TDictItem selectItemByKey(Integer itemid) {
		if(itemid==null)
			return null;
		return this.tDictItemMapper.selectByPrimaryKey(itemid);
	}

	@Override
	public void deleteItem(Integer itemid) {
		if(itemid!=null)
			this.tDictItemMapper.deleteByPrimaryKey(itemid);
		
	}

	@Override
	public void setSeq(Integer currentid, Integer targetid, Integer dictid) {
		TDictItem item1 = this.tDictItemMapper.selectByPrimaryKey(currentid);
		TDictItem item2 = this.tDictItemMapper.selectByPrimaryKey(targetid);
		if(item1==null ||item2==null){
//			throw new DmErrorException("数据有误!");	
			return;
		}
		if(item1.getItemSeq()==null ||item2.getItemSeq()==null){
//			throw new DmErrorException("数据有误没有排序大小");
			return;
		}
		Long seq = item1.getItemSeq();
		item1.setItemSeq(item2.getItemSeq());
		item2.setItemSeq(seq);
		this.tDictItemMapper.updateByPrimaryKeySelective(item1);
		this.tDictItemMapper.updateByPrimaryKeySelective(item2);
		
	}

	@Override
	public List<TDict> listTDictByTDictCode(String dictCode) {
		return this.tDictMapper.findTDictByDictCode(dictCode);
	}


}
