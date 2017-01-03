package com.dm.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

public class LimiFreeMarkerView extends FreeMarkerView{

	private static final String CONTEXT_PATH = "application";
	private static final String HTML_PATH = "htmlFolder";
	private static final String HTML_MOBILE_PATH = "htmlMobileFolder";
	@Value("${htmlMobileDir}")
	String htmlMobileFolder;

	@Value("${htmlDir}")
	String htmlFolder;
    
    @Override
    protected void exposeHelpers(Map<String, Object> model,
            HttpServletRequest request) throws Exception {
        model.put(CONTEXT_PATH, request.getContextPath());
        model.put(HTML_PATH, htmlFolder);
		model.put(HTML_MOBILE_PATH, htmlMobileFolder);
        super.exposeHelpers(model, request);
    }
}
