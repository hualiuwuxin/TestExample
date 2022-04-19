package com.lomi.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.lomi.entity.Goods;
import com.lomi.entity.in.ExecuteIn;
import com.lomi.websocket.WebSocketServer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags="json测试")
@RestController
@RequestMapping(value = "/json")
public class JsonController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(JsonController.class);

	
	@Autowired
	WebSocketServer webSocketServer;
	
	
	@ApiOperation(value = "单线程，单线程随机50W个简单对象，400毫秒")
	@RequestMapping(value = "randomEfficiency", method = { RequestMethod.GET })
	public String randomEfficiency(ExecuteIn in) throws Exception {
		System.out.println( webSocketServer );
		
		
		
		List<Goods> list = getList(10000*in.getExecuteCount());
		logger.debug( list.size()+"" );
		return "OK";
	}
	
	
	/**
	 * 不输出日志,50W次，700毫秒
	 * @param in
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "fastjson，单线程对象转json，异步输出日志,50W，700毫秒")
	@RequestMapping(value="fastjson/toJSon", method= {RequestMethod.GET})
	public String fastjsonToJSon(ExecuteIn in) throws Exception{
		List<Goods> list = getList(10000*in.getExecuteCount());
		
		for(Goods goods: list ) {
			JSONObject.toJSONString( goods );
		}
		
		return "OK";
	}
	
	
	
	String jsonFile = "C:\\Users\\ZHANGYUKUN\\Desktop\\jsonFile.txt";

	/**
	 * 向磁盘写入文件
	 * 
	 * 逐条写入1W 条耗时2613毫秒
	 * 逐条写入10W 条耗时2613毫秒
	 * 
	 * 
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "向桌面输出JSON文件")
	@RequestMapping(value="generateJsonFile", method= {RequestMethod.GET})
	public String generateJsonFile(ExecuteIn in) throws Exception{
		List<Goods> list = getList(10000*in.getExecuteCount());
		//List<String> jsonList = new ArrayList<>();
		
		File file = new File(jsonFile);
		if( file.exists() ) {
			file.delete();
		}
		file.createNewFile();
		
		for(Goods goods: list ) {
			FileUtils.writeStringToFile( new File(jsonFile), JSONObject.toJSONString( goods ) +System.getProperty("line.separator") , "utf-8", true);
		}
		
		return "OK";
	}
	
	
	/**
	 * 向磁盘写入文件
	 * 
	 * 一次写入10W 条耗时200毫秒
	 * 一次写入50W 条耗时1180毫秒
	 * 一次写入100W 条耗时2961毫秒
	 * 
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "向桌面输出JSON文件2")
	@RequestMapping(value="generateJsonFile2", method= {RequestMethod.GET})
	public String generateJsonFile2(ExecuteIn in) throws Exception{
		List<Goods> list = getList(10000*in.getExecuteCount());
		List<String> jsonList = new ArrayList<>();
		
		for(Goods goods: list ) {
			jsonList.add( JSONObject.toJSONString( goods ) );
		}
		
		File file = new File(jsonFile);
		if( file.exists() ) {
			file.delete();
		}
		file.createNewFile();
		
		
		
		FileUtils.writeLines(file, jsonList, false);
		
		
		return "OK";
	}


	
	
	
	/**
	 * 不输出日志,100W次，648毫秒
	 * 
	 * @param in
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "parse，单线程json转对象，异步输出日志,50W，846毫秒(其中有700毫秒是在生成json字符串，实际时间大概是100毫秒)")
	@RequestMapping(value="fastjson/toObject", method= {RequestMethod.GET})
	public String fastjsonToObject() throws Exception{
		File file = new File(jsonFile);
		List<String> jsonList = FileUtils.readLines(file);
		
		for(String json: jsonList ) {
			JSONObject.parseObject(json, Goods.class);
		}
		
		return "OK";
	}


	
	/**
	 * 得到指定长度的随机数组
	 * @param length
	 * @return
	 */
	private List<Goods> getList(int length) {
		List<Goods> list = new ArrayList<>();
		for( int i = 0;i<length;i++ ) {
			list.add(  Goods.randomGoods() );
		}
		return list;
	}
	
}
