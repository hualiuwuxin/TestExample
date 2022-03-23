package com.lomi.fdfs;

import java.io.IOException;
import java.util.HashSet;

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
	
	
	
	

}
