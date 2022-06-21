package com.lomi.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lomi.entity.in.validate.UserIn;
import com.lomi.entity.in.validate.UserPakeageIn;
import com.lomi.entity.out.FormatOut;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 1 @Valid 和  @Validated 效果类似,但是有写时候不一样
 * 2 在对象里面如果对象属性没有验证，而属性的属性需要验证，需要用 @Valid ，不然验证会中断
 * 3 @NotNull 修饰方法参数的时候，需要参数类 用 @Validated 修饰，这时候  @Valid 不起作用
 * @author ZHANGYUKUN
 * @Date2022年6月21日
 *
 */
@Api(tags="Validate测试")
@RestController
@RequestMapping(value = "/valid")
@Validated
public class ValidateController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ValidateController.class);

	
	
	
	@ApiOperation(value = "单参数")
	@RequestMapping(value = "t1", method = { RequestMethod.POST })
	@ResponseBody
	public FormatOut t1(   @NotNull  String  s) throws Exception {
		
		System.out.println( JSONObject.toJSONString( "调用通了" ) );
		
		return null;
	}
	
	@ApiOperation(value = "对象")
	@RequestMapping(value = "t2", method = { RequestMethod.POST })
	@ResponseBody
	public FormatOut t2(@Validated UserIn  in) throws Exception {
		
		System.out.println( JSONObject.toJSONString( "调用通了" ) );
		
		return null;
	}
	
	
	@ApiOperation(value = "2层对象")
	@RequestMapping(value = "t3", method = { RequestMethod.POST })
	@ResponseBody
	public FormatOut t2(@Valid UserPakeageIn  in) throws Exception {
		
		System.out.println( JSONObject.toJSONString( "调用通了" ) );
		
		return null;
	}
	
	
	
	
	
	
}
