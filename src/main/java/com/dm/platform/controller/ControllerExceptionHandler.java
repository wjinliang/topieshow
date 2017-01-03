package com.dm.platform.controller;

import com.dm.platform.util.CommonStatics;
import com.dm.platform.util.RequestUtil;
import com.dm.platform.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 工程：os-app
 * 创建人 : CGJ
 * 创建时间： 2015/9/6
 * 说明：
 */
@ControllerAdvice public class ControllerExceptionHandler extends DefaultController {
    private static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public void handleAllException(HttpServletResponse httpServletResponse,
        HttpServletRequest request, Exception e) {
        logger.error("捕获到异常");
        e.printStackTrace();
        try {
            if (RequestUtil.isAjax(request)) {
                logger.error("异常来源请求为：[{}],异常信息：[{}]", "ajax请求", e.getMessage());
                httpServletResponse.setContentType("text/html;charset=UTF-8");
                PrintWriter out = httpServletResponse.getWriter();
                Map map = ResponseUtil.error("服务器内部异常："+e.getMessage());
                JSONObject json = JSONObject.fromObject(map);
                out.write(json.toString());
                out.flush();
                out.close();
            } else {
                logger.error("异常来源请求为：[{}],异常信息：[{}]", "传统页面请求", e.getMessage());
                httpServletResponse.sendRedirect(CommonStatics.ERROR);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
