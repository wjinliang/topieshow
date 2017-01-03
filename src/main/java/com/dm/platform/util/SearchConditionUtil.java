package com.dm.platform.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取request 参数类
 * @project com.dm.platform.util.SearchConditionUtil.java
 * @author wjl
 * @createdate 2015年12月14日 下午3:41:35
 */
public class SearchConditionUtil {

  public static <T> Map<String, Object> packageSearchCondion(HttpServletRequest request) {
    Map<String, Object> searchCondionMap = new HashMap<String, Object>();
    Enumeration<String> paramNames = request.getParameterNames();
    while (paramNames.hasMoreElements()) {
      String paramName = (String) paramNames.nextElement();

      String[] paramValues = request.getParameterValues(paramName);
      if (paramValues.length == 1) {
        String paramValue = paramValues[0];
        if (paramValue.length() != 0) {
          searchCondionMap.put(paramName, paramValue);
        }
      }
    }
    return searchCondionMap;
  }
}
