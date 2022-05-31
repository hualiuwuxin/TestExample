package com.lomi.service;

import java.util.List;

import com.lomi.entity.Goods;

public interface GoodsService {

	void add(Goods goods);

	void addBatch(List<Goods> goodsList);
	
	/**
	 * 插入数据库的时候发送一条kafka事务消息
	 * @param goods
	 * @param throwException 
	 * @throws Exception 
	 */
	void transationKafkaMsg(Goods goods, Boolean throwException) throws Exception;
	
}
