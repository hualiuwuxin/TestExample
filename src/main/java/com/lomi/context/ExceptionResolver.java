package com.lomi.context;

import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class ExceptionResolver extends SimpleMappingExceptionResolver {

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}

	@Override
	@ExceptionHandler(value = Throwable.class)
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		System.out.println( "异常：" + ex );
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");

			PrintWriter writer = response.getWriter();
			writer.write("{\"exMsg\":\""+  "ic" +"\"}" );
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//不能返回null，不要spring或默认返回一个
		return new ModelAndView();
		//return null;
	}



}
