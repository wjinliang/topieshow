package com.dm.platform.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dm.platform.model.TDict;
import com.dm.platform.model.TDictItem;
import com.dm.platform.service.TDictService;

public class TDictCache {
	private static final Logger log = LogManager.getLogger(TDictCache.class);

	public HashMap<String, HashMap<String, String>> keyNameContainer;
	public HashMap<String, List<TDictItem>> dictItemContainer;
	public HashMap<String, String> dictJsonContainer;

	/** 保证单例 */
	static class TDictCacheHolder {
		static TDictCache instance = new TDictCache();
	}

	public static TDictCache getInstance() {
		return TDictCacheHolder.instance;
	}

	private TDictCache() {
		init();
	}

	protected void init() {
		keyNameContainer = new HashMap<String, HashMap<String, String>>();
		dictItemContainer = new HashMap<String, List<TDictItem>>();
		dictJsonContainer = new HashMap<String, String>();
	}

	public String getTDictItemNameByCode(String dictCode, String itemCode) {
		if (keyNameContainer.get(dictCode) == null)
			return "";
		else {
			return keyNameContainer.get(dictCode).get(itemCode);
		}
	}

	public List<TDictItem> getItemListByTDictCode(String dictCode) {
		return dictItemContainer.get(dictCode);
	}

	public void refreshTDict(Integer dictId, String dictCode,
			TDictService dictService) {
		if (keyNameContainer.get(dictCode) != null)
			keyNameContainer.get(dictCode).clear();
		if (dictItemContainer.get(dictCode) != null)
			dictItemContainer.get(dictCode).clear();
		Map map = new HashMap();
		TDict t = new TDict();
		t.setDictStatus(1L);//启用的字典
		map.put("model",t);
		List<TDict> d = dictService.findDictByPage(1, 200, map).getList();
		TDictCache dictCache = TDictCache.getInstance();
		List<TDictItem> itemList = dictService.selectByDictId(dictId);
		HashMap mp = new HashMap<String, String>();
		for (TDictItem item : itemList) {
			mp.put(item.getItemCode(), item.getItemName());
		}
		dictCache.keyNameContainer.put(dictCode, mp);
		dictCache.dictItemContainer.put(dictCode, itemList);
		mp = null;
		itemList = null;
	}

	// 刷新某一dictId的json数据
	public void refreshJsonDic(TDictService dictService, Integer dictId) {
		List<TDictItem> itemList = dictService.selectByDictId(dictId);;
		List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
		for (TDictItem dictItem : itemList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", dictItem.getItemId());
			map.put("itemCode", dictItem.getItemCode());
			map.put("itemName", dictItem.getItemName());
			map.put("pid",
					dictItem.getItemPid() == null ? "0" : dictItem.getItemPid());
			jsonList.add(map);
		}
		JSONArray jsonArray = JSONArray.fromObject(jsonList);
		TDictCache dictCache = TDictCache.getInstance();
		dictCache.dictJsonContainer.put(dictId.toString(), jsonArray.toString());
	}
	
	public void initAllDic(TDictService dictService){
		Map argMap = new HashMap();
		TDict t = new TDict();
		t.setDictStatus(1L);//启用的字典
		argMap.put("model",t);
		List<TDict> d = dictService.findDictByPage(1, 200, argMap).getList();
		for (TDict dict : d) {
			refreshTDict(dict.getDictId(), dict.getDictCode(), dictService);
			
		}
	}
	public void initAllJsonDic(TDictService dictService) {
		Map argMap = new HashMap();
		TDict t = new TDict();
		t.setDictStatus(1L);//启用的字典
		argMap.put("model",t);
		List<TDict> d = dictService.findDictByPage(1, 200, argMap).getList();
		for (TDict dict : d) {
			List<TDictItem> itemList = dictService.selectByDictId(dict
					.getDictId());
			List<Map<String, Object>> jsonList = new ArrayList<Map<String, Object>>();
			for (TDictItem dictItem : itemList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", dictItem.getItemId());
				map.put("itemCode", dictItem.getItemCode());
				map.put("itemName", dictItem.getItemName());
				map.put("pid",
						dictItem.getItemPid() == null ? "0" : dictItem
								.getItemPid());
				jsonList.add(map);
			}
			JSONArray jsonArray = JSONArray.fromObject(jsonList);
			dictJsonContainer.put(dict.getDictId().toString(), jsonArray.toString());
		}
	}

	public String getJsonDic(TDictService dictService, Integer dictId) {
		TDictCache dictCache = TDictCache.getInstance();
		return dictCache.dictJsonContainer.get(dictId);
	}
}