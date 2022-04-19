package com.lomi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.util.concurrent.RateLimiter;
import com.lomi.entity.in.ExecuteIn;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;



@Api(tags="限流测试")
@RestController
@RequestMapping(value = "/rate")
public class RateLimiterController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(JsonController.class);

	RateLimiter rateLimiter = RateLimiter.create(1);
	
	
	@ApiOperation(value = "限流每秒一个")
	@RequestMapping(value = "limiterOne", method = { RequestMethod.GET })
	public String t1(ExecuteIn in) throws Exception {
		
		if( rateLimiter.tryAcquire() ) {
			System.out.println( "每秒只能打印一次"  );
			return "OK";
		}else {
			throw new RuntimeException("超限了");
		}
	}
	
	
}
