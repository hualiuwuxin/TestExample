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
     * 每70秒执行一次(7分钟一个轮，每隔轮内70秒)
     */
    @Scheduled(cron = "0 0/7 * * * ?")
    @Scheduled(cron = "10 1/7 * * * ?")
    @Scheduled(cron = "20 3/7 * * * ?")
    @Scheduled(cron = "30 4/7 * * * ?")
    @Scheduled(cron = "40 5/7 * * * ?")
    @Scheduled(cron = "50 6/7 * * * ?")
    public void s1(){

        System.out.println( LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))  ) ;

    }








}
