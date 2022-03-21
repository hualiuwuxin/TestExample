package com.lomi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = "application/json")
public class LoginController{

    
    @GetMapping("/test")
    public String test(){
    	System.out.println("?????????????????????????????????");
    	
    	return "OK";
    }

 
   
}
