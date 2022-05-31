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
import com.greenpineyu.fel.compile.SourceBuilder;
import com.greenpineyu.fel.context.AbstractContext;
import com.greenpineyu.fel.context.ContextChain;
import com.greenpineyu.fel.context.FelContext;
import com.greenpineyu.fel.context.MapContext;
import com.greenpineyu.fel.function.CommonFunction;
import com.greenpineyu.fel.function.Function;
import com.greenpineyu.fel.parser.ConstNode;
import com.greenpineyu.fel.parser.FelNode;
import com.greenpineyu.fel.parser.FunNode;
import com.greenpineyu.fel.parser.VarAstNode;
import com.lomi.entity.Goods;

import net.bytebuddy.implementation.bind.annotation.Super;

public class FelTest {

	
	/**
	 * 使用常量
	 */
	@Test
	public void test0() {
		FelEngine felEngine = new FelEngineImpl();
		Object result = felEngine.eval("2*3");
		System.out.println(result);
	}
	

	/**
	 * 使用变量
	 */
	@Test
	public void test2() {
		FelEngine felEngine = new FelEngineImpl();
		FelContext ctx = felEngine.getContext();
		ctx.set("count", 10);
		ctx.set("price", 100);
		Object result = felEngine.eval("count*price");
		System.out.println(result);
	}

	/**
	 * 使用变量的方法和属性
	 */
	@Test
	public void test3() {
		FelEngine felEngine = new FelEngineImpl();
		FelContext ctx = felEngine.getContext();
		Goods good = Goods.randomGoods();
		ctx.set("good", good);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		ctx.set("map", map);

		//取不存在的方法属性,返回null
		Object result = felEngine.eval("good.size");
		System.out.println("result:" + result);

		//调用good的toString
		result = felEngine.eval("good.toString");
		System.out.println("result:" + result);

		//获取name（分别调用  name() 方法 和  getName() 方法，name(优先) ，不会取直接获取私有字段）
		result = felEngine.eval("good.name");
		System.out.println("result:" + result);
		
		//调用有参方法  name(int  i) 
		result = felEngine.eval("good.name(1)");
		System.out.println("result:" + result);
		

		//获取 map 的 value
		result = felEngine.eval("map.key1");
		System.out.println("result:" + result);
	}

	/**
	 * 使用集合，直接用下标获取 集合元素
	 */
	@Test
	public void test4() {
		FelEngine felEngine = new FelEngineImpl();
		FelContext ctx = felEngine.getContext();

		// 数组
		int[] intArray = { 1, 2, 3 };
		ctx.set("intArray", intArray);
		String exp = "intArray[0]";
		System.out.println(exp + "->" + felEngine.eval(exp));

		// List
		List<Integer> list = Arrays.asList(1, 2, 3);
		ctx.set("list", list);
		exp = "list[0]";
		System.out.println(exp + "->" + felEngine.eval(exp));

		// 集合
		Collection<String> coll = Arrays.asList("a", "b", "c");
		ctx.set("coll", coll);
		// 获取集合最前面的元素。执行结果为"a"
		exp = "coll[0]";
		System.out.println(exp + "->" + felEngine.eval(exp));

		// 迭代器
		Iterator<String> iterator = coll.iterator();
		ctx.set("iterator", iterator);
		// 获取迭代器最前面的元素。执行结果为"a"
		exp = "iterator[0]";
		System.out.println(exp + "->" + felEngine.eval(exp));

		//Map
		Map<String, String> m = new HashMap<String, String>();
		m.put("name", "HashMap");
		ctx.set("map", m);
		exp = "map.name";
		System.out.println(exp + "->" + felEngine.eval(exp));

		// 多维数组
		int[][] intArrays = { { 11, 12 }, { 21, 22 } };
		ctx.set("intArrays", intArrays);
		exp = "intArrays[0][0]";
		System.out.println(exp + "->" + felEngine.eval(exp));

		// 多维综合体，支持数组、集合的任意组合。
		List<int[]> listArray = new ArrayList<int[]>();
		listArray.add(new int[] { 1, 2, 3 });
		listArray.add(new int[] { 4, 5, 6 });
		ctx.set("listArray", listArray);
		exp = "listArray[0][0]";
		System.out.println(exp + "->" + felEngine.eval(exp));
	}

	/**
	 * 使用类的静态方法和new
	 */
	@Test
	public void test5() {
		FelEngine fel = new FelEngineImpl();
		FelContext ctx = fel.getContext();
		ctx.set("json", JSONObject.class);
		ctx.set("goods", Goods.randomGoods());

		System.out.println(fel.eval("json.toJSONString( goods )"));
		
		
		
		// 调用Math.min(1,2),java.lang包下面的都可简写
		System.out.println(FelEngine.instance.eval("$('Math').min(1,2)"));
		
		// 调用new Goods().randomGoods();
		System.out.println(FelEngine.instance.eval("$('com.lomi.entity.Goods.new').randomGoods()"));
		
		//感觉这玩意可以用链式编程动态编程(如果不考虑效率的话)
		ctx.set("name1", "张三");
		System.out.println(FelEngine.instance.eval("$('com.lomi.entity.Goods.new').setName(name1)",ctx));
		
		
	}

	/**
	 * 使用上下文的参数
	 */
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
		
		
		
		/*FelEngine felEngine = new FelEngineImpl(ctx);
		Object eval = felEngine.eval("'天气:'+天气+';温度:'+温度");
		System.out.println(eval);*/
	}

	/**
	 * 多层上下文（有继承关系，新的覆盖老的重复值）
	 */
	@Test
	public void test7() {
		FelEngine felEngine = new FelEngineImpl();
		
		FelContext ctx1 = felEngine.getContext();
		ctx1.set("a", 10);
		ctx1.set("b", 100);
		
		
		FelContext ctx2 = new ContextChain(ctx1, new MapContext());
		ctx2.set("a", 20);
		
		
		String exp = "b" + "-" + "a";
		
		
		//使用上下文2
		Object rt2 = felEngine.eval(exp, ctx2);
		System.out.println("b-a=" + rt2);
		
		
		//使用上下文1
		Object rt = felEngine.eval(exp);
		System.out.println("b-a=" + rt);

	}

	/**
	 * 编译以后在执行效率会高很多(先编译后执行，前提是公式固定)
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
	 * 大值（不知道哪里有0.9的版本）
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
	 * 子定义函数
	 */
	@Test
	public void test11() {
	    Function fun = new CommonFunction() {   
	        public String getName() {   
	            return "hello";   
	        }   
	        @Override   
	        public Object call(Object[] arguments) {   
	            Object msg = null;   
	            if(arguments!= null && arguments.length>0){   
	                msg = arguments[0];   
	            }   
	            
	            System.out.println( msg );
	            
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
	    
	    
	    
	    FelEngine felEngine = new FelEngineImpl();   
	    //添加函数到引擎中。   
	    felEngine.addFun(fun);   
	    felEngine.addFun(addFun);
	    
	    
	    String exp = "hello('你好')";   
	    //解释执行   
	    Object eval = felEngine.eval(exp);   
	    System.out.println("hello "+eval);   
	    
	    
	    //编译执行(编译的过程会直接执行一边，如果这时候 修改了 上下文的变量，就要注意了)
	    Expression compile = felEngine.compile(exp, null);   
	    eval = compile.eval(null);   
	    System.out.println("hello "+eval);
	    
	    
	    //函数里面可以套函数( CommonFunction.call.(Object[] arguments) 方法里面拿到的都是已经吧内嵌函数计算好了，详情见父类的 CommonFunction.call(FelNode node, FelContext context) )
	    System.out.println( felEngine.eval("hello( add(3,2) )") );
	}
	
	
	
	
	/**
	 * 效率比较，正常简单相加20亿次，5秒
	 */
	@Test
	public void test12() {
		
		Long start = System.currentTimeMillis();
		Integer a = 0;
		Add add = new Add();
		for(int i = 0;i<200;i++) {
			a= (Integer) add.call( new Integer[] {a,1} );
		}
		Long end = System.currentTimeMillis();
		System.out.println( a );
		System.out.println( end-start );
	}
	
	/**
	 * 解释执行也就差5000 倍，10W次6 秒。
	 */
	@Test
	public void test13() {
		
		Long start = System.currentTimeMillis();
		FelEngine felEngine = new FelEngineImpl();   
		felEngine.addFun( new Add() );
		Integer a = 0;
		for(int i = 0;i<100;i++) {
			a = (Integer) felEngine.eval("add("+ a +",1)");
		}
		Long end = System.currentTimeMillis();
		System.out.println( a );
		System.out.println( end-start );
	}
	
	
	/**
	 * 解释执行也就差10多20 倍，2亿次9 秒
	 */
	@Test
	public void test14() {
		
		Long start = System.currentTimeMillis();
		FelEngine felEngine = new FelEngineImpl();   
		felEngine.addFun( new Add2() );
		Object a = 0;
		FelContext felContext = felEngine.getContext();
		felContext.set("a", 0 );
		
		//不知道为啥编译会把int类型改成double。
		Expression expression  = felEngine.compile("add(a,1)",felContext);
		
		for(int i = 0;i<200;i++) {
			a =  expression.eval(felContext);
		}
		
		Long end = System.currentTimeMillis();
		System.out.println( a );
		System.out.println( end-start );
	}
	
	
	
	@Test
	public void test15() {
		FelEngine felEngine = new FelEngineImpl();   
		felEngine.addFun( new Add() );
		FelContext  context = felEngine.getContext();
		context.set("a", Arrays.asList(3,6,9));
		context.set("b", Arrays.asList(2,1,3));
		context.set("c", Arrays.asList(2,2,2));
		
		
		Object evalRt = felEngine.eval("add(add(a,b),c)");
		System.out.println( JSONObject.toJSON( evalRt ) );
		
		Object evalRt2 = felEngine.eval("(a+b)+c");
		System.out.println( JSONObject.toJSON( evalRt2 ) );
		
		Object evalRt3 = felEngine.eval("add((a+b),c)");
		System.out.println( JSONObject.toJSON( evalRt3 ) );
		
	}
	
	/**
	 * 覆盖 com.greenpineyu.fel.function.operator 下面的 +，-，*，/,=,等可以从定义这些符号，比如让  1==2 返回true
	 */
	@Test
	public void testEqual() {
		FelEngine felEngine = new FelEngineImpl();   
		
		Object evalRt = felEngine.eval("1==2");
		System.out.println( evalRt );
		
	}
	
	
	
	class Add extends CommonFunction{
		
		@Override
		public String getName() {
			return "add";
		}

		@Override
		public Object call(FelNode node, FelContext context) {
			return super.call(node, context);
		}

		@Override
		public SourceBuilder toMethod(FelNode node, FelContext ctx) {
			return super.toMethod(node, ctx);
		}


		@Override
		public Object call(Object[] arguments) {
			Object args0 = arguments[0];
			Object args1 = arguments[1];
			
			if( args0 instanceof List || args1 instanceof List ) {
				if( args0 instanceof List && !(args1 instanceof List)  ) {
					List list0 = (List)args0;
					return (Integer)(list0.get(0)) + (Integer)args1;
				}
				if( !(args0 instanceof List) && (args1 instanceof List)  ) {
					List list1 = (List)args1;
					return (Integer)(list1.get(0)) + (Integer)args0;
				}
				
				List<Integer> rt = new ArrayList<>();
				List list0 = (List)args0;
				List list1 = (List)args1;
				for( int i = 0;i<list0.size()&&i<list1.size();i++ ) {
					rt.add( (Integer)list0.get(i) + (Integer)list1.get(i) );
				}
				return rt;
			}else {
				return (Integer)args0 + (Integer)args1;
			}
		}
	}
	
	
	
	class Add2 extends CommonFunction{
		FelContext context;
		
		public Object call(FelNode node, FelContext context) {
			Object[] children = evalArgs(node, context);
			this.context = context;
			return call(children);
		}
		
		
		@Override
		public String getName() {
			return "add";
		}

		@Override
		public Integer call(Object[] arguments) {
			System.out.println(  JSONObject.toJSONString( arguments ) );
			Integer a = (Integer)arguments[0] + (Integer)arguments[1];
			context.set("a", a);
			return a;
		}
	}
	
	
	
	
	
	
	

}
