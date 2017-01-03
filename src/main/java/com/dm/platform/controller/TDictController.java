package com.dm.platform.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dm.cms.model.CmsContent;
import com.dm.platform.dto.TreeNode;
import com.dm.platform.model.TDict;
import com.dm.platform.model.TDictItem;
import com.dm.platform.service.TDictService;
import com.dm.platform.util.PageConvertUtil;
import com.dm.platform.util.ResponseUtil;
import com.dm.platform.util.TDictCache;
import com.dm.platform.util.TDictUtil;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/dict")
public class TDictController {
	
	@Autowired
	TDictService tDictService;
	
	@RequestMapping("/page")
	public String page(){
		return "/admin/dict/page";
	}
	@RequestMapping("/ajaxList")
	@ResponseBody
	public Object ajaxList(
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			TDict tDict){
		Map map = new HashMap();
		map.put("model", tDict);
		PageInfo<TDict> page = tDictService.findDictByPage(
				pageNum, pageSize, map);
		return PageConvertUtil.grid(page);
	}
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Object insertOrUpdate(TDict tDict){
		return this.tDictService.insertOrUpdate(tDict);
	}
	@RequestMapping("delete")
	@ResponseBody
	public Object delete(int id){
		Map map = this.tDictService.deleteDict(id);
		return map;
	}
	@RequestMapping("refreshCache")
	@ResponseBody
	public Object refreshCache(Integer dictId){
		TDict t = this.tDictService.selectByPrimaryKey(dictId);
		TDictCache.getInstance().refreshTDict(dictId, t.getDictCode(), tDictService);
		TDictCache.getInstance().refreshJsonDic(tDictService, dictId);
		return ResponseUtil.success("操作成功！");
	}
	@RequestMapping("/dict/{dictId}")
	public String itemPage(){
		return "/admin/dict/item/page";
	}
	@RequestMapping("/items/{dictId}")
	@ResponseBody
	public List<TreeNode> getByDictId(@PathVariable Integer dictId)
	{
		TDict tDict = tDictService.selectByPrimaryKey(dictId);
		
		List<TDictItem> tDictItems = tDictService.selectByDictId(dictId);
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		for(TDictItem item:tDictItems)
		{
			TreeNode treeNode = new TreeNode();
			treeNode.setId(item.getItemId());
			treeNode.setName(item.getItemName());
			treeNode.setpId(item.getItemPid()==null?tDict.getDictId():item.getItemPid());
			treeNodes.add(treeNode);
		}
		TreeNode treeNode = new TreeNode();
		treeNode.setId(tDict.getDictId());
        treeNode.setName(tDict.getDictName());
        treeNodes.add(treeNode);
		return treeNodes;
	}
	@RequestMapping("/items/setSeq")
	@ResponseBody
	public Object setSeq(
			@RequestParam(value = "currentid", required = false) Integer currentid,
			@RequestParam(value = "targetid", required = false) Integer targetid,
			@RequestParam(value = "dictid", required = false) Integer dictid){
		this.tDictService.setSeq(currentid,targetid,dictid);
		return ResponseUtil.success("操作成功！");
	}
	@RequestMapping("/items/ajaxSave")
	@ResponseBody
	public Object save(TDictItem iDictItem){
		this.tDictService.insertOrUpdateItem(iDictItem);
		return ResponseUtil.success("操作成功！");
	}
	@RequestMapping("/items/ajaxLoad")
	@ResponseBody
	public Object load(Integer itemid){
		return this.tDictService.selectItemByKey(itemid);
	}
	@RequestMapping("/items/ajaxDelete")
	@ResponseBody
	public Object delete(Integer itemid){
		this.tDictService.deleteItem(itemid);
		return ResponseUtil.success("操作成功！");
	}
	@RequestMapping("/getItems")
	@ResponseBody
	public Object getLocal(String code){
		List<TDictItem> list =TDictUtil.itemList(code);
		if(list==null){
			return Collections.emptyList();
		}
		List<Map> ls = new ArrayList<Map>();
		for(TDictItem item:list){
			Map mp = new HashMap();
			mp.put("text", item.getItemName());
			mp.put("value", item.getItemCode());
			ls.add(mp);
		}
		 return ls;
	}
//	@ExceptionHandler(DmErrorException.class)
//	public Object ExceptionHandler(DmErrorException e){
//		return ResponseUtil.error(e.getErr());
//	}
}
