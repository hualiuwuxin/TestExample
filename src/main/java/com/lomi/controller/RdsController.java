package com.lomi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lomi.entity.Goods;
import com.lomi.entity.in.ExecuteIn;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


//@Api(tags="rds测试")
//@RestController
///@RequestMapping(value = "/rds")
public class RdsController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(RdsController.class);

	 @Autowired
	 RedisTemplate<Object, Object> redisTemplateJson;
	
	
	 
	 
	@ApiOperation(value = "")
	@RequestMapping(value="insert/singleThread", method= {RequestMethod.GET})
	public String singleThread(ExecuteIn in) throws Exception{
		
		redisTemplateJson.opsForValue().set("goods1",  Goods.randomGoods() );
		
		Goods goods1 = (Goods) redisTemplateJson.opsForValue().get("goods1");
		System.out.println(  goods1 );
		
		return "OK";
	}
	
	
	
	
	

	
}
