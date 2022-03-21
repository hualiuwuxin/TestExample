package com.lomi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lomi.entity.Goods;

public interface GoodsExMapper extends GoodsMapper {
	
	/**
	 * 批量插入
	 * @param list
	 */
	void addBatch(@Param("list") List<Goods> list);
   
}