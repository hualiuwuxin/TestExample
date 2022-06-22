package com.lomi.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.lomi.entity.in.validate.UserIn;
import com.lomi.entity.in.validate.UserPakeageIn;
import com.lomi.entity.out.FormatOut;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 1 @Valid 和  @Validated 效果类似,但是有写时候不一样
 * 2 在对象里面如果对象属性没有验证，而属性的属性需要验证，需要用 @Valid ，不然验证会中断
 * 3 @NotNull 修饰方法参数的时候，需要在参数方法所在类上标注 @Validated 修饰，这时候  @Valid 不起作用
 * 4 在标注方法参数的时候 @NotNull ，@NotBlank @NotEmpty 都一样，只是判断是否空
 * 
 * 
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
	public FormatOut t1(@NotBlank  String  s) throws Exception {
		
		System.out.println( JSONObject.toJSONString( s ) );
		
		return null;
	}
	
	@ApiOperation(value = "对象")
	@RequestMapping(value = "t2", method = { RequestMethod.POST })
	@ResponseBody
	public String t2(@RequestBody @Validated UserIn  in) throws Exception {
		
		System.out.println( JSONObject.toJSONString( in ) );
		if( in != null ){
			throw new  Exception("抛出异常");
		}



		return "OK";
	}
	
	
	@ApiOperation(value = "2层对象")
	@RequestMapping(value = "t3", method = { RequestMethod.POST })
	@ResponseBody
	public FormatOut t2(  @Valid UserPakeageIn  in) throws Exception {
		
		System.out.println( JSONObject.toJSONString( in ) );
		
		return null;
	}
	
	
	
	
	
	
}
