package com.lomi.limitFlow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.google.common.util.concurrent.RateLimiter;

/**
 * 限流
 * @author ZHANGYUKUN
 *
 */
public class RateLimiterTest {
	/**
	 * 
	 *  1，RateLimiter 原理：下一次请求的时候，通过当前时间和上次生成最后一个令牌的时间算一个时间差，然算出这段时间应该生成多少令牌，然后计算可用牌数够不够，不够返回没有令牌（非阻塞），或者计算出一个令牌数量够了的时间点，睡眠等待时间达到然后返回（阻塞方式）
	 *  2，另外 RateLimiter 有允许刚开始多发一小段时间(预消费 nextFreeTicketMicros )
	 *  3，RateLimiter 精确到 每微秒 N个 令牌（ double stableIntervalMicros; ），小于微秒以后就不是平每微秒都增加了
	 */
	
	DateFormat df = new SimpleDateFormat("yyyy-MM=dd HH:mm:ss:SSS");
	
	
	/**
	 * 使用常量，每秒1000，打印毫秒数，基本令牌1毫秒增加一个
	 */
	@Test
	public void test1() {
		RateLimiter rateLimiter = RateLimiter.create(1000);
		
		for(int i=0;i<1000;) {
			boolean canRun = rateLimiter.tryAcquire();
			if( canRun ) {
				System.out.println("第"+ i +"+个:时间" + System.nanoTime()/1000000 );
				i++;
			}
		}
	}
	

	
	
	/**
	 * 使用常量，每秒10000，打印0.1微秒，基本令牌0.1毫秒增加一个
	 */
	@Test
	public void test2() {
		
		RateLimiter rateLimiter = RateLimiter.create(10000);
		
		for(int i=0;i<10000;) {
			boolean canRun = rateLimiter.tryAcquire();
			if( canRun ) {
				System.out.println("第"+ i +"+个:时间" + System.nanoTime()/100000 );
				i++;
			}
		}
	}
	
	/**
	 * 使用常量，每秒10000，打印0.01微秒，基本令牌0.01毫秒增加一个
	 */
	@Test
	public void test3() {
		RateLimiter rateLimiter = RateLimiter.create(1000);
		
		for(int i=0;i<100;) {
			double canRun = rateLimiter.acquire();
			System.out.println(canRun );
			System.out.println("第"+ i +"+个:时间" + df.format( new Date() ));
			i++;
		}
	}
	
	

	

}
