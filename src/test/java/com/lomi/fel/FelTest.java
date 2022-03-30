package com.lomi.fel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.greenpineyu.fel.Expression;
import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.common.ObjectUtils;
import com.greenpineyu.fel.context.AbstractContext;
import com.greenpineyu.fel.context.ContextChain;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.context.MapContext;
import com.greenpineyu.fel.function.CommonFunction;
import com.greenpineyu.fel.function.Function;
import com.lomi.entity.Goods;

public class FelTest {

	// 使用常量
	@Test
	public void test() {
		FelEngine fel = new FelEngineImpl();
		Object result = fel.eval("5000*12+7500");
		System.out.println(result);
	}

	// 使用变量
	@Test
	public void test2() {
		FelEngine fel = new FelEngineImpl();
		FelContext ctx = fel.getContext();
		ctx.set("单价", 5000);
		ctx.set("数量", 12);
		ctx.set("运费", 7500);
		Object result = fel.eval("单价*数量+运费");
		System.out.println(result);
	}

	// 使用变量的方法和属性
	@Test
	public void test3() {
		FelEngine fel = new FelEngineImpl();
		FelContext ctx = fel.getContext();
		Goods foo = Goods.randomGoods();
		ctx.set("foo", foo);
		Map<String, String> m = new HashMap<String, String>();
		m.put("ElName", "fel");
		ctx.set("m", m);

		// 调用foo.getSize()方法。
		Object result = fel.eval("foo.size");
		System.out.println("result:" + result);

		// 调用foo.isSample()方法。
		result = fel.eval("foo.toString");
		System.out.println("result:" + result);

		// foo没有name、getName、isName方法
		// foo.name会调用foo.get("name")方法。
		result = fel.eval("foo.name");
		System.out.println("result:" + result);

		// m.ElName会调用m.get("ElName");
		result = fel.eval("m.ElName");
		System.out.println("result:" + result);
	}

	// 使用集合
	@Test
	public void test4() {
		FelEngine fel = new FelEngineImpl();
		FelContext ctx = fel.getContext();

		// 数组
		int[] intArray = { 1, 2, 3 };
		ctx.set("intArray", intArray);
		// 获取intArray[0]
		String exp = "intArray[0]";
		System.out.println(exp + "->" + fel.eval(exp));

		// List
		List<Integer> list = Arrays.asList(1, 2, 3);
		ctx.set("list", list);
		// 获取list.get(0)
		exp = "list[0]";
		System.out.println(exp + "->" + fel.eval(exp));

		// 集合
		Collection<String> coll = Arrays.asList("a", "b", "c");
		ctx.set("coll", coll);
		// 获取集合最前面的元素。执行结果为"a"
		exp = "coll[0]";
		System.out.println(exp + "->" + fel.eval(exp));

		// 迭代器
		Iterator<String> iterator = coll.iterator();
		ctx.set("iterator", iterator);
		// 获取迭代器最前面的元素。执行结果为"a"
		exp = "iterator[0]";
		System.out.println(exp + "->" + fel.eval(exp));

		// Map
		Map<String, String> m = new HashMap<String, String>();
		m.put("name", "HashMap");
		ctx.set("map", m);
		exp = "map.name";
		System.out.println(exp + "->" + fel.eval(exp));

		// 多维数组
		int[][] intArrays = { { 11, 12 }, { 21, 22 } };
		ctx.set("intArrays", intArrays);
		exp = "intArrays[0][0]";
		System.out.println(exp + "->" + fel.eval(exp));

		// 多维综合体，支持数组、集合的任意组合。
		List<int[]> listArray = new ArrayList<int[]>();
		listArray.add(new int[] { 1, 2, 3 });
		listArray.add(new int[] { 4, 5, 6 });
		ctx.set("listArray", listArray);
		exp = "listArray[0][0]";
		System.out.println(exp + "->" + fel.eval(exp));
	}

	// 使用java对象的方法
	@Test
	public void test5() {

		// JSONObject.toJSONString(getClass())

		FelEngine fel = new FelEngineImpl();
		FelContext ctx = fel.getContext();
		ctx.set("json", JSONObject.class);
		ctx.set("goods", Goods.randomGoods());

		System.out.println(fel.eval("json.toJSONString( goods )"));
	}

	// 使用java对象的方法
	@Test
	public void test6() {
		// 负责提供气象服务的上下文环境
		FelContext ctx = new AbstractContext() {
			public Object get(String name) {
				if ("天气".equals(name)) {
					return "晴";
				}
				if ("温度".equals(name)) {
					return 25;
				}
				return null;
			}
		};
		FelEngine fel = new FelEngineImpl(ctx);
		Object eval = fel.eval("'天气:'+天气+';温度:'+温度");
		System.out.println(eval);

	}

	// 多层空间
	@Test
	public void test7() {
		FelEngine fel = new FelEngineImpl();
		String costStr = "成本";
		String priceStr = "价格";
		FelContext baseCtx = fel.getContext();
		// 父级上下文中设置成本和价格
		baseCtx.set(costStr, 50);
		baseCtx.set(priceStr, 100);

		String exp = priceStr + "-" + costStr;
		Object baseCost = fel.eval(exp);
		System.out.println("期望利润：" + baseCost);

		FelContext ctx = new ContextChain(baseCtx, new MapContext());
		// 通货膨胀导致成本增加（子级上下文 中设置成本，会覆盖父级上下文中的成本）
		ctx.set(costStr, 50 + 20);

		Object allCost = fel.eval(exp, ctx);
		System.out.println("实际利润：" + allCost);

	}

	/**
	 * 先编译后执行
	 */
	@Test
	public void test8() {
		FelEngine fel = new FelEngineImpl();
		FelContext ctx = fel.getContext();
		ctx.set("单价", 5000);
		ctx.set("数量", 12);
		ctx.set("运费", 7500);
		Expression exp = fel.compile("单价*数量+运费", ctx);
		Object result = exp.eval(ctx);
		System.out.println(result);
	}

	/**
	 * 大值
	 */
	@Test
	public void test9() {
		/*
		 * FelEngine fel = FelBuilder.bigNumberEngine(); String input =
		 * "111111111111111111111111111111+22222222222222222222222222222222"; Object
		 * value = fel.eval(input); Object compileValue = fel.compile(input,
		 * fel.getContext()).eval(fel.getContext()); System.out.println("大数值计算（解释执行）:" +
		 * value); System.out.println("大数值计算（编译执行）:" + compileValue);
		 */
	}

	/**
	 * 静态方法
	 */
	@Test
	public void test10() {

		
		// 调用Math.min(1,2),java.lang包下面的都可简写
		System.out.println(FelEngine.instance.eval("$('Math').min(1,2)"));
		// 调用new Foo().toString();
		System.out.println(FelEngine.instance.eval("$('com.lomi.entity.Goods.new').randomGoods()"));
	}
	
	/**
	 * 静态方法
	 */
	@Test
	public void test11() {
		
		//定义hello函数   
	    Function fun = new CommonFunction() {   
	  
	        public String getName() {   
	            return "hello";   
	        }   
	           
	        /*   
	         * 调用hello("xxx")时执行的代码  
	         */   
	        @Override   
	        public Object call(Object[] arguments) {   
	            Object msg = null;   
	            if(arguments!= null && arguments.length>0){   
	                msg = arguments[0];   
	            }   
	            return "say:" + ObjectUtils.toString(msg);   
	        }   
	  
	    };  
	    
	    
	    Function addFun = new CommonFunction() {   
	  	  
	        public String getName() {   
	            return "add";   
	        }   
	           
	        @Override   
	        public Object call(Object[] arguments) {   
	        	if(arguments== null || arguments.length != 2){   
	              throw new RuntimeException("参数异常");
	            }   
	        	
	        	int a = (int)arguments[0];
	        	int b = (int)arguments[1];
	        	
	            return a+b;   
	        }   
	  
	    };  
	    
	    
	    
	    
	    
	    FelEngine e = new FelEngineImpl();   
	    //添加函数到引擎中。   
	    e.addFun(fun);   
	    e.addFun(addFun);
	    
	    
	    String exp = "hello('fel')";   
	    //解释执行   
	    Object eval = e.eval(exp);   
	    System.out.println("hello "+eval);   
	    //编译执行   
	    Expression compile = e.compile(exp, null);   
	    eval = compile.eval(null);   
	    System.out.println("hello "+eval);
	    
	    
	    
	    
	    System.out.println( e.eval("hello( add(3,2) )") );
	    
	    
	    
	    
	    
	}

}
