package com.lomi.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags="MDC测试")
@RestController
@RequestMapping(value = "/mdc")
public class MDCController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(MDCController.class);
	
	 /**
	  * 全集请求Id key
	  */
	 private static final String REQUSETIDKEY = "requestId";
	
	/**
	 * id生成器
	 */
	 Snowflake snowflake =  IdUtil.getSnowflake();
	 
	 
	@ApiOperation(value = "单线程插入并且取回")
	@RequestMapping(value="setAndGet", method= {RequestMethod.GET})
	public String setAndGet() throws Exception{
		String id = snowflake.nextIdStr();
		System.out.println("生成的请求Id：" + id );
		MDC.put(REQUSETIDKEY, id );
		
		System.out.println("获取到的请求Id：" + MDC.get(REQUSETIDKEY) );
		return "OK";
	}
	
	
	@ApiOperation(value = "子线程插入并且默认取不到")
	@RequestMapping(value="setAndGetSub", method= {RequestMethod.GET})
	public String setAndGetSub() throws Exception{
		String id = snowflake.nextIdStr();
		System.out.println("生成的请求Id：" + id );
		MDC.put(REQUSETIDKEY, id );
		
		
		System.out.println("主线程获取到的请求Id：" + MDC.get(REQUSETIDKEY) );
		
		
		new Thread() {

			@Override
			public void run() {
				System.out.println("子线程获取到的请求Id：" + MDC.get(REQUSETIDKEY) );
			}
			
		}.start();
		
		return "OK";
	}
	
	
	
	/**
	 * 正常这么搞，但是还有在日志上面配置全局继承 MDC容器（默认值 isThreadContextMapInheritable=false ，一般不建议默认开启）
	 *
	 * 值得注意的是，在线程池，Timer 等不 子线程对象是公用的情景，手动 getCopyOfContextMap 是不能满足需求的（transmittable-thread-local 包可以）
	 *
	 * @return
	 * @throws Exception
	 * @author ZHANGYUKUN
	 * @Date 2022年6月1日
	 *
	 */
	@ApiOperation(value = "子线程插入并且取到")
	@RequestMapping(value="getCopyOfContextMap", method= {RequestMethod.GET})
	public String getCopyOfContextMap() throws Exception{
		String id = snowflake.nextIdStr();
		System.out.println("生成的请求Id：" + id );
		MDC.put(REQUSETIDKEY, id );
		
		
		System.out.println("主线程获取到的请求Id：" + MDC.get(REQUSETIDKEY) );
		
		//复制父线程上下文
		Map<String, String> map = MDC.getCopyOfContextMap();
		new Thread() {
			@Override
			public void run() {
				//给子类设置上下文
				MDC.setContextMap( map );
				
				System.out.println("子线程获取到的请求Id：" + MDC.get(REQUSETIDKEY) );
			}
			
		}.start();
		
		return "OK";
	}
	
	
	

	
}
