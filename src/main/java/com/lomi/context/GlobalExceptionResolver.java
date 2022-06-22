package com.lomi.context;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 这种写法，没法处理 @Valid 的验证的异常，原因是 验证类异常出现在进入方法之前
 *
 * 简单的统一异常处理
 * @author ZHANGYUKUN
 * @date 2022/6/22
 */
//@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    /**
     *{"msg":true}
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println( "异常：" + ex );
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

            PrintWriter  writer = response.getWriter();
            writer.write("{\"=exMsg\":"+  ex.getMessage() +"}" );
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //不能返回null，不要spring或默认返回一个
        return new ModelAndView();
        //return null;
    }
}