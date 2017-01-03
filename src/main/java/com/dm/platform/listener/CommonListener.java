package com.dm.platform.listener;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dm.platform.service.TDictService;
import com.dm.platform.util.EhCacheUtil;
import com.dm.platform.util.RandomValidateCode;
import com.dm.platform.util.TDictCache;
 


public class CommonListener extends ContextLoaderListener {
	private static Logger log = LogManager.getLogger(CommonListener.class);

	private static WebApplicationContext ct;
    
	public CommonListener() {
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			System.out.println("----------------------授权通过---------------------");
			System.out.println("------------------------------------------------");
			System.out.println("----------------                ----------------");
			System.out.println("----------------  topieCms正在启动      ---------------");
			System.out.println("----------------                ----------------");
			System.out.println("------------------------------------------------");
			ct = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
			initRandomVcode();
			EhCacheUtil.getInstance().init(ct);
			 TDictService dictService = (TDictService)ct.getBean("tDictServiceImpl");
			TDictCache.getInstance().initAllJsonDic(dictService);
			TDictCache.getInstance().initAllDic(dictService);
			log.info("----------------topieCms启动成功！----------------");
		} catch (Exception ce) {
			ce.printStackTrace();
			log.error("初始化数据异常：", ce);
		}
	}
	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
	public void initRandomVcode(){
		RandomValidateCode.getInstance().getRandcode();
	}

}
