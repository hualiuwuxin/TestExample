package com.lomi.fdfs;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.atomic.LongAdder;

import org.csource.common.MyException;
import org.junit.Test;

import cn.hutool.core.util.IdUtil;

public class HutoolTest {
	
	@Test
	public void initSnowflake() throws IOException, MyException {
		
		Long start = System.currentTimeMillis();
		
		HashSet<Long> set = new HashSet<>();
		for(int i = 0;i<1000000;i++   ) {
			set.add( IdUtil.getSnowflakeNextId() );
			//System.out.println(  );
		}
		
		System.out.println( set.size() );
		
		Long end = System.currentTimeMillis();
		System.out.println( end-start );
	}
	
	@Test
	public void initAuto() throws IOException, MyException {
		LongAdder la = new LongAdder();
		
		Long start = System.currentTimeMillis();
		
		HashSet<Long> set = new HashSet<>();

		for(int i = 0;i<100;i++   ) {
			la.increment();
			
			System.out.println( la.longValue() );
			long id = System.currentTimeMillis()+la.longValue();
			System.out.println( id );
			set.add( id );
		}
		
		System.out.println( set.size() );
		
		Long end = System.currentTimeMillis();
		System.out.println( end-start );
	}
	
	
	
	

}
