package com.lomi.lamdda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.lomi.entity.Goods;

/**
 * lamdda 测试
 * 
 * @author  ZHANGYUKUN
 * @Date  2022/6/8
 */
public class LamddaTest {


	@Test
	public void test1(){
		Consumer<String> sysout = System.out::println;

		sysout.accept("哇哈哈哈哈");


	}


	/**
	 * 类::实例方法 格式的 lamdda 表达式  等价于 a(类,返回值) 接口函数
	 */
	@Test
	public void test2(){
		BiFunction<Integer,Integer,Integer> bif = (Integer a,Integer b)->{
			return a+b;
		};

		Comparator<String> comparator = String::compareTo;

		Goods goods = Goods.randomGoods();

		//实例::实例方法 指向实例方法
		Supplier<Long> supplier =  goods::getId;
		System.out.println(supplier.get());

		//类::类方法  等于   a(参数,返回值)
		Supplier<Double> f2 =  Math::random;


		//类::实例方法 格式的 lamdda 表达式  等价于 a(类,返回值) 接口函数
		Function<Goods,Long> f = Goods::getId;



	}


	/**
	 * 类::实例方法 格式的 lamdda 表达式  等价于 a(类,返回值) 接口函数
	 *
	 * @param
	 * @return: void
	 * @Author: ZHANGYUKUN
	 * @Date: 2022/6/13
	 */
	@Test
	public void test3(){

		//使用 lamdda 表达式 在一个对象get方法上包一层
		Function<Goods,Long>	function = p->{
			return p.getId();
		};

		System.out.println( function.apply(Goods.randomGoods()) );


		//使用 lamdda 表达式 在 set 方法上包一层
		BiConsumer<Goods,Long>	biConsumer = (p1,p2)->{
			 p1.setId( p2 );
		};

		Goods goods = Goods.randomGoods();
		biConsumer.accept( goods,1L );
		System.out.println( goods.getId() );




	}


	/**
	 * 构造器引用
	 *
	 * @param
	 * @return: void
	 * @Author: ZHANGYUKUN
	 * @Date: 2022/6/13
	 */
	@Test
	public void test4(){
		//使用无参构造器
		Supplier<Goods> consumer = Goods::new;
		System.out.println(JSONObject.toJSONString( consumer.get()));


		//使用有参数的构造器
		Function<String,Goods> consumer2 = Goods::new;
		System.out.println(JSONObject.toJSONString( consumer2.apply("aaaaa") ));

		//数组引用
		Function<Integer,String[]> consumer3 = String[]::new;
		System.out.println(JSONObject.toJSONString( consumer3.apply(10 ) ));

		//集合引用
		Supplier<List<String>> supplier4 = ArrayList<String>::new;
		Function<Integer,List<String>> function5 = ArrayList<String>::new;

		//String[] a = new String[]{"1","2","3"};
		//String[] b = new String[10];



	}




	/**
	 * 构成stram 的几种方法
	 *
	 */
	@Test
	public void stram1(){
		Goods[] array = new Goods[10];
		array[0] = Goods.randomGoods();

		List<Goods> list = new ArrayList<>();
		/*for(  int i=0;i<10;i++){
			list.add( Goods.randomGoods() );
		}*/

		//集合的 stream
		list = list.stream().collect(Collectors.toList()  );


		//数组的stream
		list = Arrays.stream(array).collect(Collectors.toList());

		//Stream
		Stream.of( array ).forEach( System.out::println );
		System.out.println( JSONObject.toJSONString( list ) );
	}


	/**
	 * stram 无限流
	 *
	 */
	@Test
	public void stram2(){
		//Stream
		Stream.iterate( 0, item->++item ).limit(100).forEach( System.out::println );

		Stream.generate( ()-> Goods.randomGoods() ).limit(100).forEach( System.out::println );
	}


	/**
	 * stram 无限流
	 *
	 */
	@Test
	public void stram3(){
		Goods[] array = new Goods[3];
		array[0] = Goods.randomGoods();
		array[1] = Goods.randomGoods();
		array[2] = Goods.randomGoods();
		List<String> data = Arrays.asList("1","2","9","0");
		List<String> dataNull = Arrays.asList();

		//Stream
		//Stream.of( array ).flatMap();
		
		//排序(实现Comparable的可以直接排序，升序)
		System.out.println("自带排序：" + Stream.of( 1,3,9,5 ).sorted().collect( Collectors.toList() )  );
		
		//或者传入Comparator
		List<Goods> list2 = Stream.of( array ).sorted(new Comparator<Goods>() {
			@Override
			public int compare(Goods o1, Goods o2) {
				return (int)(o1.getId()-o2.getId());
			}
		}).collect( Collectors.toList() );
		System.out.println("指定排序：" + JSONObject.toJSONString( list2 )  );
		
		
		
		
		//reduce
		Optional<String>  op = data.stream().reduce( (a,b)->a+b );
		System.out.println( op.get() );
		
		Optional<String>  op2 = dataNull.stream().reduce( (a,b)->a+b );
		System.out.println( op2.orElseGet( ()->"1" ) );
		//System.out.println( op2.orElseThrow(()-> new RuntimeException("为空则抛出") ) );
		//System.out.println( op2.orElseThrow(()-> new RuntimeException("为空则抛出") ) );
		
		
		
		
			
		//toCollection

		//summarize

		//分组分区

		//collectingAndThen
	}



	/**
	 * 接口新特性
	 */
	@Test
	public void ds(){
		Straw straw = new Straw();
		straw.eat();
		straw.deat1();
		straw.deat2();
		straw.deat1();
		straw.deat2();


	}



}
