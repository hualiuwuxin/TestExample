package com.lomi;

import java.util.function.Consumer;

public class Main {

	public static void main(String[] args) {
		System.out.println( Integer.MAX_VALUE );
		System.out.println( Long.MAX_VALUE );

		Consumer c = System.out::println;

		c.accept( "张玉坤" );



	}

}
