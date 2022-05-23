package com.lomi.config.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Scheduling2 {

    /**
     * 每70秒执行一次(7分钟一个轮，每隔轮内间隔70秒)
     */
    @Scheduled(cron = "0 0/7 * * * ?")
    @Scheduled(cron = "10 1/7 * * * ?")
    @Scheduled(cron = "20 2/7 * * * ?")
    @Scheduled(cron = "30 3/7 * * * ?")
    @Scheduled(cron = "40 4/7 * * * ?")
    @Scheduled(cron = "50 5/7 * * * ?")
    public void s1(){

        System.out.println( LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))  ) ;

    }








}
