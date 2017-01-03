package com.dm.platform.util;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dm.platform.model.TDict;
import com.dm.platform.model.TDictItem;
import com.dm.platform.service.TDictService;

public class TDictUtil{
	public static ApplicationContext ct = new ClassPathXmlApplicationContext(
			"config/spring/applicationContext.xml");
	public static TDictService dictService = (TDictService)ct.getBean("tDictServiceImpl");
	public static String toName(String dictCode,String itemCode){
		return TDictCache.getInstance().getTDictItemNameByCode(dictCode, itemCode);
	}
	public static List<TDictItem> itemList(String dictCode){
		return TDictCache.getInstance().getItemListByTDictCode(dictCode);
	}
	public static void refreshTDict(Integer dictId,String dictCode,TDictService dictService){
		TDictCache.getInstance().refreshTDict(dictId, dictCode, dictService);
		TDictCache.getInstance().refreshJsonDic(dictService, dictId);
	}
	public static String getJson(Integer dictId){
		return TDictCache.getInstance().getJsonDic(dictService, dictId);
	}
	public static String getJsonByTDictCode(String dictCode){
		Integer dictId ;
		List<TDict> list = dictService.listTDictByTDictCode(dictCode);
		if(list.size()>0){
			dictId=list.get(0).getDictId();
			return TDictCache.getInstance().getJsonDic(dictService, dictId);
		}else{
			return "";
		}
	}
}