package com.lomi.proxy;

import java.lang.reflect.Proxy;

import org.junit.Test;

import com.lomi.lamdda.Food;

public class ProxyTest {

	@Test
	public void test() {
		Class cls = Proxy.getProxyClass(ProxyTest.class.getClassLoader(),  Food.class );
	}

}
