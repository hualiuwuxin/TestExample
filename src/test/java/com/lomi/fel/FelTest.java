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
import com.greenpineyu.fel.parser.FelNode;
import com.lomi.entity.Goods;

public class FelTest {

	
	/**
	 * ʹ�ó���
	 */
	@Test
	public void test0() {
		FelEngine felEngine = new FelEngineImpl();
		Object result = felEngine.eval("2*3");
		System.out.println(result);
	}
	

	/**
	 * ʹ�ñ���
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
	 * ʹ�ñ����ķ���������
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

		//ȡ�����ڵķ�������,����null
		Object result = felEngine.eval("good.size");
		System.out.println("result:" + result);

		//����good��toString
		result = felEngine.eval("good.toString");
		System.out.println("result:" + result);

		//��ȡname���ֱ����  name() ���� ��  getName() ������name(����) ������ȡֱ�ӻ�ȡ˽���ֶΣ�
		result = felEngine.eval("good.name");
		System.out.println("result:" + result);
		
		//�����вη���  name(int  i) 
		result = felEngine.eval("good.name(1)");
		System.out.println("result:" + result);
		

		//��ȡ map �� value
		result = felEngine.eval("map.key1");
		System.out.println("result:" + result);
	}

	/**
	 * ʹ�ü��ϣ�ֱ�����±��ȡ ����Ԫ��
	 */
	@Test
	public void test4() {
		FelEngine felEngine = new FelEngineImpl();
		FelContext ctx = felEngine.getContext();

		// ����
		int[] intArray = { 1, 2, 3 };
		ctx.set("intArray", intArray);
		String exp = "intArray[0]";
		System.out.println(exp + "->" + felEngine.eval(exp));

		// List
		List<Integer> list = Arrays.asList(1, 2, 3);
		ctx.set("list", list);
		exp = "list[0]";
		System.out.println(exp + "->" + felEngine.eval(exp));

		// ����
		Collection<String> coll = Arrays.asList("a", "b", "c");
		ctx.set("coll", coll);
		// ��ȡ������ǰ���Ԫ�ء�ִ�н��Ϊ"a"
		exp = "coll[0]";
		System.out.println(exp + "->" + felEngine.eval(exp));

		// ������
		Iterator<String> iterator = coll.iterator();
		ctx.set("iterator", iterator);
		// ��ȡ��������ǰ���Ԫ�ء�ִ�н��Ϊ"a"
		exp = "iterator[0]";
		System.out.println(exp + "->" + felEngine.eval(exp));

		//Map
		Map<String, String> m = new HashMap<String, String>();
		m.put("name", "HashMap");
		ctx.set("map", m);
		exp = "map.name";
		System.out.println(exp + "->" + felEngine.eval(exp));

		// ��ά����
		int[][] intArrays = { { 11, 12 }, { 21, 22 } };
		ctx.set("intArrays", intArrays);
		exp = "intArrays[0][0]";
		System.out.println(exp + "->" + felEngine.eval(exp));

		// ��ά�ۺ��壬֧�����顢���ϵ�������ϡ�
		List<int[]> listArray = new ArrayList<int[]>();
		listArray.add(new int[] { 1, 2, 3 });
		listArray.add(new int[] { 4, 5, 6 });
		ctx.set("listArray", listArray);
		exp = "listArray[0][0]";
		System.out.println(exp + "->" + felEngine.eval(exp));
	}

	/**
	 * ʹ����ľ�̬������new
	 */
	@Test
	public void test5() {
		FelEngine fel = new FelEngineImpl();
		FelContext ctx = fel.getContext();
		ctx.set("json", JSONObject.class);
		ctx.set("goods", Goods.randomGoods());

		System.out.println(fel.eval("json.toJSONString( goods )"));
		
		
		
		// ����Math.min(1,2),java.lang������Ķ��ɼ�д
		System.out.println(FelEngine.instance.eval("$('Math').min(1,2)"));
		
		// ����new Goods().randomGoods();
		System.out.println(FelEngine.instance.eval("$('com.lomi.entity.Goods.new').randomGoods()"));
		
		//�о��������������ʽ��̶�̬���(���������Ч�ʵĻ�)
		ctx.set("name1", "����");
		System.out.println(FelEngine.instance.eval("$('com.lomi.entity.Goods.new').setName(name1)",ctx));
		
		
	}

	/**
	 * ʹ�������ĵĲ���
	 */
	@Test
	public void test6() {
		// �����ṩ�������������Ļ���
		FelContext ctx = new AbstractContext() {
			public Object get(String name) {
				if ("����".equals(name)) {
					return "��";
				}
				if ("�¶�".equals(name)) {
					return 25;
				}
				return null;
			}
		};
		
		
		
		FelEngine felEngine = new FelEngineImpl(ctx);
		Object eval = felEngine.eval("'����:'+����+';�¶�:'+�¶�");
		System.out.println(eval);
	}

	/**
	 * ��������ģ��м̳й�ϵ���µĸ����ϵ��ظ�ֵ��
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
		
		
		//ʹ��������2
		Object rt2 = felEngine.eval(exp, ctx2);
		System.out.println("b-a=" + rt2);
		
		
		//ʹ��������1
		Object rt = felEngine.eval(exp);
		System.out.println("b-a=" + rt);

	}

	/**
	 * �����Ժ���ִ��Ч�ʻ�ߺܶ�(�ȱ����ִ�У�ǰ���ǹ�ʽ�̶�)
	 */
	@Test
	public void test8() {
		FelEngine fel = new FelEngineImpl();
		FelContext ctx = fel.getContext();
		ctx.set("����", 5000);
		ctx.set("����", 12);
		ctx.set("�˷�", 7500);
		Expression exp = fel.compile("����*����+�˷�", ctx);
		Object result = exp.eval(ctx);
		System.out.println(result);
	}

	/**
	 * ��ֵ����֪��������0.9�İ汾��
	 */
	@Test
	public void test9() {
		/*
		 * FelEngine fel = FelBuilder.bigNumberEngine(); String input =
		 * "111111111111111111111111111111+22222222222222222222222222222222"; Object
		 * value = fel.eval(input); Object compileValue = fel.compile(input,
		 * fel.getContext()).eval(fel.getContext()); System.out.println("����ֵ���㣨����ִ�У�:" +
		 * value); System.out.println("����ֵ���㣨����ִ�У�:" + compileValue);
		 */
	}
	
	/**
	 * �Ӷ��庯��
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
	              throw new RuntimeException("�����쳣");
	            }   
	        	
	        	int a = (int)arguments[0];
	        	int b = (int)arguments[1];
	            return a+b;   
	        }   
	    };  
	    
	    
	    
	    FelEngine felEngine = new FelEngineImpl();   
	    //��Ӻ����������С�   
	    felEngine.addFun(fun);   
	    felEngine.addFun(addFun);
	    
	    
	    String exp = "hello('���')";   
	    //����ִ��   
	    Object eval = felEngine.eval(exp);   
	    System.out.println("hello "+eval);   
	    
	    
	    //����ִ��(����Ĺ��̻�ֱ��ִ��һ�ߣ������ʱ�� �޸��� �����ĵı�������Ҫע����)
	    Expression compile = felEngine.compile(exp, null);   
	    eval = compile.eval(null);   
	    System.out.println("hello "+eval);
	    
	    
	    //������������׺���( CommonFunction.call.(Object[] arguments) ���������õ��Ķ����Ѿ�����Ƕ����������ˣ����������� CommonFunction.call(FelNode node, FelContext context) )
	    System.out.println( felEngine.eval("hello( add(3,2) )") );
	}
	
	
	
	
	/**
	 * Ч�ʱȽϣ����������20�ڴΣ�5��
	 */
	@Test
	public void test12() {
		
		Long start = System.currentTimeMillis();
		Integer a = 0;
		Add add = new Add();
		for(int i = 0;i<2000000000;i++) {
			a= (Integer) add.call( new Integer[] {a,1} );
		}
		Long end = System.currentTimeMillis();
		System.out.println( a );
		System.out.println( end-start );
	}
	
	/**
	 * ����ִ��Ҳ�Ͳ�5000 ����10W��6 �롣
	 */
	@Test
	public void test13() {
		
		Long start = System.currentTimeMillis();
		FelEngine felEngine = new FelEngineImpl();   
		felEngine.addFun( new Add() );
		Integer a = 0;
		for(int i = 0;i<1000000;i++) {
			a = (Integer) felEngine.eval("add("+ a +",1)");
		}
		Long end = System.currentTimeMillis();
		System.out.println( a );
		System.out.println( end-start );
	}
	
	
	/**
	 * ����ִ��Ҳ�Ͳ�10��20 ����2�ڴ�9 ��
	 */
	@Test
	public void test14() {
		
		Long start = System.currentTimeMillis();
		FelEngine felEngine = new FelEngineImpl();   
		felEngine.addFun( new Add2() );
		Object a = 0;
		FelContext felContext = felEngine.getContext();
		felContext.set("a", 0 );
		
		//��֪��Ϊɶ������int���͸ĳ�double��
		Expression expression  = felEngine.compile("add(a,1)",felContext);
		
		for(int i = 0;i<200000000;i++) {
			a =  expression.eval(felContext);
		}
		
		Long end = System.currentTimeMillis();
		System.out.println( a );
		System.out.println( end-start );
	}
	
	
	class Add extends CommonFunction{
		@Override
		public String getName() {
			return "add";
		}

		@Override
		public Object call(Object[] arguments) {
			return (Integer)arguments[0] + (Integer)arguments[1];
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
			Integer a = (Integer)arguments[0] + (Integer)arguments[1];
			context.set("a", a);
			return a;
		}
	}
	
	
	
	
	
	
	

}
