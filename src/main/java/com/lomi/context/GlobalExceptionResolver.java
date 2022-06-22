package com.lomi.context;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * 1 @Component 这种写法，没法处理 @Valid 的验证的异常，原因是 验证类异常出现在进入方法之前
 * 2 如果需要拦截 验证异常需要 @ControllerAdvice ，可以拦截到进入，并且需要指定@ExceptionHandler(value = Throwable.class)
 *
 * 简单的统一异常处理
 * @author ZHANGYUKUN
 * @date 2022/6/22
 */
//@Component
@ControllerAdvice
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
    @ExceptionHandler(value = Throwable.class)
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println( "异常：" + ex );

        String  exMsg = null;
        if( ex instanceof MethodArgumentNotValidException){
            exMsg = ((MethodArgumentNotValidException)ex).getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        }else{
            exMsg = ex.getMessage();
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");

            PrintWriter writer = response.getWriter();
            writer.write("{\"exMsg\":\""+  exMsg +"\"}" );
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //不能返回null，不要spring或默认返回一个
        return new ModelAndView();
        //return null;
    }
}