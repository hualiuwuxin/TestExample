package com.lomi;

import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.lomi.mapper")
@EnableScheduling
@EnableKafka
public class Application {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args); 
        System.out.println("*****************************");
        System.out.println("********* TestExample 启动................... ***********");
        System.out.println("*****************************"); 
        System.in.read(); 

        
    }
    
}
