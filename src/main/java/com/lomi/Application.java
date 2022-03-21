package com.lomi;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude={
		DataSourceAutoConfiguration.class,
		RedisAutoConfiguration.class})
@ComponentScan("com.lomi") 
//@ImportResource({"classpath:META-INF/spring/spring-main.xml"})
@EnableScheduling
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args); 
        System.out.println("*****************************");
        System.out.println("********* TestExample Æô¶¯................... ***********");
        System.out.println("*****************************"); 
        System.in.read(); 
    }
    
}
