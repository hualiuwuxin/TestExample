package com.lomi.lamdda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
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

	/**
	 * lamdda 表达式的写法
	 */
	@Test
	public void test1(){
		//传统写法，匿名内部类
		Function<String,String> funOld = new Function<String, String>() {
			@Override
			public String apply(String t) {
				return t;
			}
		};
		
		
		//lamdda表达式完整写法
		Function<String,String> funAll = (String item)-> {
			return item;
		};
		
		//参数类型可以省略,因为可以通过泛型推断得出
		Function<String,String> fun1 = (item)-> {
			return item;
		};
		
		//只有一个参数的时候参数扩号可以省略
		Function<String,String> fun2 = item-> {
			return item;
		};
		
		//只有一行代码并且，那么这个行代码的值就是返回值，return和大括号也可以剩余
		Function<String,String> fun3 = item->item;
		
		//使用类名或者对象名::方法名字，可以得到对应方法的引用。
		Consumer<String> c = System.out::println;
		
		
		//lamdda 表达式可以看做是 一接口函数的实现。 每一个方法都可以被指定规则的接口函数指向使用 类::实例方法的时候，需要接口函数的第一个参数就是这个 类的实例对象。
		//接口函数，有且只有一个方法的接口，并且这个方法不能是 defualt 和 static的
	}


	/**
	 * 最基础的函数接口 和以前常用的一些方法对应关系
	 * 
	 * 类::实例方法 格式的 lamdda 表达式  等价于 a(类,返回值) 接口函数
	 */
	@Test
	public void test2(){
		Goods goods = Goods.randomGoods();
		
		//很多已经存在的方法可以指向对应参数的接口函数，下面三种最基础的函数接口
		
		//Consumer 接口函数，只有一个参数没有返回值
		Consumer<String> sysout = System.out::println;
		sysout.accept("哇哈哈哈哈");
		
		//Supplier接口函数，只有返回值
		Supplier<String> supplier1 = goods::getData;
		
		
		//Function接口函数，一个参数一个返回值
		Function<Double,Long> fun = Math::round;
		
		List<Goods> list = new ArrayList<>();
		//Predicate,有一个参数，返回是否，是 function的 特殊情况
		Predicate<Goods> predicate = list::contains;
		
		
		//2个参数的Function
		BiFunction<Integer,Integer,Integer> bif = (Integer a,Integer b)->{
			return a+b;
		};

		//实例::实例方法 指向实例方法
		Supplier<Long> supplier =  goods::getId;
		System.out.println(supplier.get());
		

		//类::类方法  等于   a(参数,返回值)
		Supplier<Double> f2 =  Math::random;
		System.out.println( f2.get() );


		//类::实例方法 格式的 lamdda 表达式  等价于 a(类,返回值) 接口函数(接口函数或者lamdda表达式的第一个参数是类实例)
		Function<Goods,Long> f = Goods::getId;
		Function<Goods,Long> ff = item->item.getId();
		f.apply(  Goods.randomGoods() );
		ff.apply(  Goods.randomGoods() );
		

		// Comparable.compareTo 接口函数 的实例可以指向 Comparator
		Comparator<String> comparator = String::compareTo;
		
	}


	/**
	 * 使用函数接口调用被 lamdda 表达式包裹的对象 get，set 方法
	 *
	 * @param
	 * @return: void
	 * @Author: ZHANGYUKUN
	 * @Date: 2022/6/13
	 */
	@Test
	public void test3(){

		//使用 lamdda 表达式 在一个对象get方法上包一层
		Function<Goods,Long> function = p->{
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
	 * stram 无限流，按照指定规则，无限生成元素，然后无限循环
	 *
	 */
	@Test
	public void stram2(){
		//Stream
		Stream.iterate( 0, item->++item ).limit(100).forEach( System.out::println );

		Stream.generate( ()-> Goods.randomGoods() ).limit(100).forEach( System.out::println );
	}


	/**
	 * stram 流的常见用法
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
		
		
		
		

		List<Goods> list = new ArrayList<>();

		//流生产集合
		list = list.stream().collect(Collectors.toList()  );


		//数组转集合
		list = Arrays.stream(array).collect(Collectors.toList());

		//遍历
		Stream.of( array ).forEach( System.out::println );
		System.out.println( JSONObject.toJSONString( list ) );

		
		//排序(实现Comparable的可以直接排序，升序)
		System.out.println("自带排序：" + Stream.of( 1,3,9,5 ).sorted().collect( Collectors.toList() )  );
		
		//没实现Comparable的元素的集合可以通过传入Comparator实现排序，下面通过ID排序
		List<Goods> list2 = Stream.of( array ).sorted(new Comparator<Goods>() {
			@Override
			public int compare(Goods o1, Goods o2) {
				return (int)(o1.getId()-o2.getId());
			}
		}).collect( Collectors.toList() );
		System.out.println("指定排序：" + JSONObject.toJSONString( list2 )  );
		
		
		//toArray，感觉没什么卵用，类型也错还会报错,保证类型正确的情况下可以把集合转换成数组 
		String[] ar =  data.stream().toArray( item-> {
			 return new String[item];
		 }  );
		System.out.println( "数组：" +ar.length );
		
		
		 
		//peek 不会终止流的遍历(而且是是用的元素前调用的，调试很好用)
		data.stream().peek(System.out::println).forEach(System.out::println);
		
		
		List<List<Goods>> list22 = new ArrayList<>();
		list22.add(  Arrays.asList( Goods.randomGoods(),Goods.randomGoods() ) );
		list22.add(  Arrays.asList( Goods.randomGoods(),Goods.randomGoods() ) );
		list22.add(  Arrays.asList( Goods.randomGoods(),Goods.randomGoods() ) );
		
		
		//flatMap把多维度的集合展开成一维集合，例子是把二维集合，展开然后ID求和
		Optional<Long>  sum = list22.stream().flatMap( item->item.stream() ).map(Goods::getId).reduce( Math::addExact );
		System.out.println("求和:" +  sum.orElse(-1L) );
		
		
		//reduce,可以理解成把一个集合的数据合并起来(mapreduce 的 合并类似)
		Optional<String>  op = data.stream().reduce( (a,b)->a+b );
		System.out.println( op.get() );
		
		
		//Optional 可以减少空指针的辅助类
		Optional<String>  op2 = dataNull.stream().reduce( (a,b)->a+b );
		
		//取到到就给默认值
		System.out.println( op2.orElseGet( ()->"1" ) );
		//System.out.println( op2.orElseThrow(()-> new RuntimeException("为空则抛出") ) );
		
		Optional.of("只能包装非空对象");
		Optional.ofNullable(null);
		
		
			
		//toCollection(把结果装换成任意集合)
		List<String> aaa =  dataNull.stream().collect( Collectors.toCollection(()->new LinkedList<String>())  );
		System.out.println( "toCollection:" + aaa.getClass() );
		

		
		//summarize(汇总,然后可以知己去平均值，最大最小值，数量等等)
		DoubleSummaryStatistics doubleSummaryStatistics  = dataNull.stream().collect( Collectors.summarizingDouble( item->Double.valueOf(item) ) );
		System.out.println( doubleSummaryStatistics.getMax() );
		System.out.println( doubleSummaryStatistics.getMin() );
		System.out.println( doubleSummaryStatistics.getAverage() );
		 
		 
		 

		//分组(分组的第一个值是分组键，第二个是包装组的容器，第三个是元素集合)
	 	Map<Long,Set<Goods>> map =    list2.stream().collect( Collectors.groupingBy( Goods::getId ,LinkedHashMap::new, Collectors.toSet() ) );
		System.out.println( map.getClass() );
		
		
		//分区(相当于只有两个组的分组)
		Map<Boolean, List<Goods>> pt  =  list2.stream().collect( Collectors.partitioningBy((item->true))  );
		System.out.println(  pt.get(true).size() );
		System.out.println(  pt.get(false).size() );
		
		

		//collectingAndThen(先生产集合，然后处理一下),比如分组然后取到所有分组key
		Set<Long> keySet =  list2.stream().collect( Collectors.collectingAndThen(Collectors.groupingBy( Goods::getId ,LinkedHashMap::new, Collectors.toSet() ), item->item.keySet() )  );
		System.out.println(  keySet );
		 
		 
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
