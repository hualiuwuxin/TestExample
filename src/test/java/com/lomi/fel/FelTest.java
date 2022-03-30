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

	// ʹ�ó���
	@Test
	public void test() {
		FelEngine fel = new FelEngineImpl();
		Object result = fel.eval("5000*12+7500");
		System.out.println(result);
	}

	// ʹ�ñ���
	@Test
	public void test2() {
		FelEngine fel = new FelEngineImpl();
		FelContext ctx = fel.getContext();
		ctx.set("����", 5000);
		ctx.set("����", 12);
		ctx.set("�˷�", 7500);
		Object result = fel.eval("����*����+�˷�");
		System.out.println(result);
	}

	// ʹ�ñ����ķ���������
	@Test
	public void test3() {
		FelEngine fel = new FelEngineImpl();
		FelContext ctx = fel.getContext();
		Goods foo = Goods.randomGoods();
		ctx.set("foo", foo);
		Map<String, String> m = new HashMap<String, String>();
		m.put("ElName", "fel");
		ctx.set("m", m);

		// ����foo.getSize()������
		Object result = fel.eval("foo.size");
		System.out.println("result:" + result);

		// ����foo.isSample()������
		result = fel.eval("foo.toString");
		System.out.println("result:" + result);

		// fooû��name��getName��isName����
		// foo.name�����foo.get("name")������
		result = fel.eval("foo.name");
		System.out.println("result:" + result);

		// m.ElName�����m.get("ElName");
		result = fel.eval("m.ElName");
		System.out.println("result:" + result);
	}

	// ʹ�ü���
	@Test
	public void test4() {
		FelEngine fel = new FelEngineImpl();
		FelContext ctx = fel.getContext();

		// ����
		int[] intArray = { 1, 2, 3 };
		ctx.set("intArray", intArray);
		// ��ȡintArray[0]
		String exp = "intArray[0]";
		System.out.println(exp + "->" + fel.eval(exp));

		// List
		List<Integer> list = Arrays.asList(1, 2, 3);
		ctx.set("list", list);
		// ��ȡlist.get(0)
		exp = "list[0]";
		System.out.println(exp + "->" + fel.eval(exp));

		// ����
		Collection<String> coll = Arrays.asList("a", "b", "c");
		ctx.set("coll", coll);
		// ��ȡ������ǰ���Ԫ�ء�ִ�н��Ϊ"a"
		exp = "coll[0]";
		System.out.println(exp + "->" + fel.eval(exp));

		// ������
		Iterator<String> iterator = coll.iterator();
		ctx.set("iterator", iterator);
		// ��ȡ��������ǰ���Ԫ�ء�ִ�н��Ϊ"a"
		exp = "iterator[0]";
		System.out.println(exp + "->" + fel.eval(exp));

		// Map
		Map<String, String> m = new HashMap<String, String>();
		m.put("name", "HashMap");
		ctx.set("map", m);
		exp = "map.name";
		System.out.println(exp + "->" + fel.eval(exp));

		// ��ά����
		int[][] intArrays = { { 11, 12 }, { 21, 22 } };
		ctx.set("intArrays", intArrays);
		exp = "intArrays[0][0]";
		System.out.println(exp + "->" + fel.eval(exp));

		// ��ά�ۺ��壬֧�����顢���ϵ�������ϡ�
		List<int[]> listArray = new ArrayList<int[]>();
		listArray.add(new int[] { 1, 2, 3 });
		listArray.add(new int[] { 4, 5, 6 });
		ctx.set("listArray", listArray);
		exp = "listArray[0][0]";
		System.out.println(exp + "->" + fel.eval(exp));
	}

	// ʹ��java����ķ���
	@Test
	public void test5() {

		// JSONObject.toJSONString(getClass())

		FelEngine fel = new FelEngineImpl();
		FelContext ctx = fel.getContext();
		ctx.set("json", JSONObject.class);
		ctx.set("goods", Goods.randomGoods());

		System.out.println(fel.eval("json.toJSONString( goods )"));
	}

	// ʹ��java����ķ���
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
		FelEngine fel = new FelEngineImpl(ctx);
		Object eval = fel.eval("'����:'+����+';�¶�:'+�¶�");
		System.out.println(eval);

	}

	// ���ռ�
	@Test
	public void test7() {
		FelEngine fel = new FelEngineImpl();
		String costStr = "�ɱ�";
		String priceStr = "�۸�";
		FelContext baseCtx = fel.getContext();
		// ���������������óɱ��ͼ۸�
		baseCtx.set(costStr, 50);
		baseCtx.set(priceStr, 100);

		String exp = priceStr + "-" + costStr;
		Object baseCost = fel.eval(exp);
		System.out.println("��������" + baseCost);

		FelContext ctx = new ContextChain(baseCtx, new MapContext());
		// ͨ�����͵��³ɱ����ӣ��Ӽ������� �����óɱ����Ḳ�Ǹ����������еĳɱ���
		ctx.set(costStr, 50 + 20);

		Object allCost = fel.eval(exp, ctx);
		System.out.println("ʵ������" + allCost);

	}

	/**
	 * �ȱ����ִ��
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
	 * ��ֵ
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
	 * ��̬����
	 */
	@Test
	public void test10() {

		
		// ����Math.min(1,2),java.lang������Ķ��ɼ�д
		System.out.println(FelEngine.instance.eval("$('Math').min(1,2)"));
		// ����new Foo().toString();
		System.out.println(FelEngine.instance.eval("$('com.lomi.entity.Goods.new').randomGoods()"));
	}
	
	/**
	 * ��̬����
	 */
	@Test
	public void test11() {
		
		//����hello����   
	    Function fun = new CommonFunction() {   
	  
	        public String getName() {   
	            return "hello";   
	        }   
	           
	        /*   
	         * ����hello("xxx")ʱִ�еĴ���  
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
	              throw new RuntimeException("�����쳣");
	            }   
	        	
	        	int a = (int)arguments[0];
	        	int b = (int)arguments[1];
	        	
	            return a+b;   
	        }   
	  
	    };  
	    
	    
	    
	    
	    
	    FelEngine e = new FelEngineImpl();   
	    //��Ӻ����������С�   
	    e.addFun(fun);   
	    e.addFun(addFun);
	    
	    
	    String exp = "hello('fel')";   
	    //����ִ��   
	    Object eval = e.eval(exp);   
	    System.out.println("hello "+eval);   
	    //����ִ��   
	    Expression compile = e.compile(exp, null);   
	    eval = compile.eval(null);   
	    System.out.println("hello "+eval);
	    
	    
	    
	    
	    System.out.println( e.eval("hello( add(3,2) )") );
	    
	    
	    
	    
	    
	}

}
