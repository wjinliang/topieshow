package com.dm.atform.directive;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dm.atform.service.AtTableService;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
 * 分类或表总个数 (type)DataResourceRecordDirective.java
 * @author Mr.Jin
 *
 */
public class DirectoryNumberDirective implements TemplateDirectiveModel{

	private Logger log  = LoggerFactory.getLogger(DirectoryNumberDirective.class);
	@Autowired
	private AtTableService tableService;
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Object type = params.get("type");
		String t = type==null?null: type.toString();
		Map map = new HashMap();
		map.put("status", "1");
		map.put("type",t);
		Long c = tableService.countByArg(map);
		env.setVariable("directoryNumber",ObjectWrapper.DEFAULT_WRAPPER.wrap(c));
		body.render(env.getOut());  
	}

}
