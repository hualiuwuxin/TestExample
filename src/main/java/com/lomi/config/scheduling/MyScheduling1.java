package com.lomi.config.scheduling;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import cn.hutool.core.date.LocalDateTimeUtil;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * 自定义定时器
 */
//@Component
public class MyScheduling1 implements SchedulingConfigurer {

    ExecutorService ex = new ThreadPoolExecutor(1,
            10,30,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue(100) );


    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {

  

        Runnable task = ()->{
            ex.execute( ()->System.out.println( LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))  )  );
        };

        PeriodicTrigger periodicTrigger = new PeriodicTrigger(5,TimeUnit.SECONDS);
        registrar.addTriggerTask(task, a -> {
                    return new CronTrigger("0/5 * * * * ?").nextExecutionTime(a);
                });

    }
}
