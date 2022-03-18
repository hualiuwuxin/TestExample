package com.lomi.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.lomi.utils.page.PageList;


/**
 * bean的工具类
 * 
 * @author ZHANGYUKUN
 *
 */
public class BeanUtils {

	/**
	 * 复制一个对象到另一个对象，忽略null值字段
	 * 
	 * 
	 * @param source
	 * @param target
	 * @param ignoreNull
	 */
	public static void copyProperties(Object source, Object target, Boolean ignoreNull) {
		if (target == null) {
			return;
		}

		if (source == null) {
			return;
		}

		if (!ignoreNull) {
			org.springframework.beans.BeanUtils.copyProperties(source, target);
		} else {
			String[] ignoreFiled = getNullField(source);
			org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreFiled);
		}

	}

	public static void copyProperties(Object source, Object target) {
		copyProperties(source, target, false);
	}

	/**
	 * 创建并复制一个对象
	 * 
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> T copyNew(Object source, Class<T> targetCls) {
		if (source == null) {
			return null;
		}

		T rt;
		try {
			rt = targetCls.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		org.springframework.beans.BeanUtils.copyProperties(source, rt);
		return rt;
	}

	/**
	 * 复制信息到outList
	 * 
	 * @param list
	 * @param outCls
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "resource" })
	public static <T> List<T> copyToOutList(List<?> list, Class<T> outCls) {
		if (list == null) {
			return null;
		}
		List<T> rtList = null;
		try {
			rtList = list.getClass().getDeclaredConstructor().newInstance();

			if (list instanceof com.github.pagehelper.Page) {
				com.github.pagehelper.Page<Object> pageList = (com.github.pagehelper.Page<Object>) rtList;
				com.github.pagehelper.Page<Object> temp = (com.github.pagehelper.Page<Object>) list;
				pageList.setPageNum(temp.getPageNum());
				pageList.setPageSize(temp.getPageSize());
				pageList.setPages(temp.getPages());
				pageList.setTotal(temp.getTotal());
			}

			for (Object item : list) {
				T rtItem = outCls.getDeclaredConstructor().newInstance();
				BeanUtils.copyProperties(item, rtItem, false);
				rtList.add(rtItem);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rtList;
	}

	/**
	 * 复制分页对象
	 * @param <T>
	 * @param list 原数据
	 * @param outCls 输出对象类
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public  static  <T> PageList<T> copyPageList(List<?> list,Class<T> outCls) {
		String[] ignoreFiled = new String[] { "result" };
		PageList<T> pageList = null;
		try {
			pageList = PageList.class.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if( list instanceof com.github.pagehelper.Page ) {
			com.github.pagehelper.Page<T> pageListIn = (com.github.pagehelper.Page<T>) list;
			
			pageList.setPageNum(pageListIn.getPageNum());
			pageList.setPageSize(pageListIn.getPageSize());
			pageList.setPages(pageListIn.getPages());
			pageList.setTotal(pageListIn.getTotal());
			
			pageList.setList( copyToOutList(pageListIn.getResult(), outCls) );
		}else {
			pageList.setList( copyToOutList(list, outCls) );	
		}
		
		return pageList;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public  static  <T> PageList<T> copyPageList(List<T> list) {
		String[] ignoreFiled = new String[] { "result" };
		PageList<T> pageList = null;
		try {
			pageList = PageList.class.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if( list instanceof com.github.pagehelper.Page ) {
			com.github.pagehelper.Page<T> pageListIn = (com.github.pagehelper.Page<T>) list;
			
			pageList.setPageNum(pageListIn.getPageNum());
			pageList.setPageSize(pageListIn.getPageSize());
			pageList.setPages(pageListIn.getPages());
			pageList.setTotal(pageListIn.getTotal());
			
			pageList.setList( pageListIn.getResult() );
		}else {
			pageList.setList( list );	
		}
		
		return pageList;
	}
	
	

	/**
	 * 得到 值为null 的字段 （只找当前类，没找父类，因为我们的实体暂时没有继承关系）
	 * 
	 * @param source
	 * @return
	 */
	public static String[] getNullField(Object source) {
		List<String> fieldList = new ArrayList<>();
		Field[] fields = source.getClass().getDeclaredFields();

		for (Field field : fields) {
			field.setAccessible(true);
			try {
				if (field.get(source) == null) {
					fieldList.add(field.getName());
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}

		}

		return fieldList.toArray(new String[] {});
	}

	/**
	 * 得到定义的所有字段（返回数组）
	 * 
	 * @return
	 */
	public static String[] getDeclareField(Class<?> cls) {
		return getDeclareFieldAsList(cls).toArray(new String[] {});
	}

	/**
	 * 得到定义的所有字段(返回list)
	 * 
	 * @return
	 */
	public static List<String> getDeclareFieldAsList(Class<?> cls) {
		List<String> fieldList = new ArrayList<>();
		Field[] fields = cls.getDeclaredFields();

		for (Field field : fields) {
			fieldList.add(field.getName());
		}

		return fieldList;
	}

	/**
	 * 得到枚举数组的 names
	 * 
	 * @param enums
	 * @return 枚举的names
	 */
	public static List<String> enumArraysNames(Enum<?>[] enums) {
		List<String> names = new ArrayList<>();
		for (Enum<?> em : enums) {
			names.add(em.name());
		}
		return names;
	}

	
    /**
     * 	忽略值为空字符串的字段 （ 处理前端 带上 name=name&name2=&name4=&name3=   这种  带了  key 但是 不传值得问题  ）
     * @param in
     */
	public static void ignoreNullStrField(Object in) {
		if( in == null ) {
			return ;
		}
		
		Class<?> cls = in.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			//获取字段值
			Object fieldValue = null;
			try {
				field.setAccessible(true);
				fieldValue = field.get(in);
				
				
				if( fieldValue instanceof String ) {
					if( "".equals( fieldValue )  ) {
						field.set( in , null);
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}
	
}
