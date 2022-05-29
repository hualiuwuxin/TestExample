package com.lomi.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lomi.context.SpringContextHoder;
import com.lomi.entity.Goods;
import com.lomi.service.GoodsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags="kafka测试")
@RestController
@RequestMapping(value = "/db")
public class KafkaController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(KafkaController.class);

	@Autowired
	GoodsService goodsService;
	
	
	
	@ApiOperation(value = "测试事务消息")
	@RequestMapping(value="transationMsg", method= {RequestMethod.GET})
	public String transationMsg(Boolean  throwException) throws Exception{
		
		Map<String,DataSourceTransactionManager> map = SpringContextHoder.getContext().getBeansOfType(DataSourceTransactionManager.class );
		Map<String,PlatformTransactionManager> map2 = SpringContextHoder.getContext().getBeansOfType(PlatformTransactionManager.class );
		
		System.out.println(  map );
		System.out.println(  map2 );
		
		goodsService.transationKafkaMsg( Goods.randomGoods() ,throwException );
		
		
		return "OK";
	}
	
}
