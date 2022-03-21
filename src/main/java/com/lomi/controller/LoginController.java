package com.lomi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lomi.entity.Goods;
import com.lomi.service.GoodsService;

@RestController
@RequestMapping(produces = "application/json")
public class LoginController{

	@Autowired
	GoodsService goodsService;
	
	
    @GetMapping("/test")
    public String test(){
    	System.out.println("?????????????????????????????????");
    	
    	Goods goods = new Goods();
    	goods.setId(1L);
    	
    	
    	goodsService.add(goods);
    	
    	
    	return "OK";
    }

 
   
}
