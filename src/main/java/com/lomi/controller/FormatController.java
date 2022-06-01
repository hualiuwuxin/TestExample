package com.lomi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lomi.entity.in.FormatIn;
import com.lomi.entity.out.FormatOut;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags="格式化测试")
@RestController
@RequestMapping(value = "/format")
public class FormatController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(FormatController.class);

	
	
	
	@ApiOperation(value = "post_格式化")
	@RequestMapping(value = "post", method = { RequestMethod.POST })
	@ResponseBody
	public FormatOut post(FormatIn in) throws Exception {
		
		FormatOut out= new FormatOut();
		
		return out;
	}
	
	
	
	@ApiOperation(value = "get_格式化")
	@RequestMapping(value = "get", method = { RequestMethod.GET })
	@ResponseBody
	public FormatOut get(FormatIn in) throws Exception {
		
		FormatOut out= new FormatOut();
		
		return out;
	}
	

	@ApiOperation(value = "json_格式化")
	@RequestMapping(value = "json", method = { RequestMethod.POST })
	@ResponseBody
	public FormatOut json(@RequestBody @Validated FormatIn in) throws Exception {
		
		System.out.println( in.getDate() );
		
		FormatOut out= new FormatOut();
		return out;
	}
	
	
	
	
}
