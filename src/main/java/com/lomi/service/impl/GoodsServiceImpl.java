package com.lomi.service.impl;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.lomi.annotation.ShowParam;
import com.lomi.entity.Goods;
import com.lomi.mapper.GoodsExMapper;
import com.lomi.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {


	
	@Autowired
	GoodsExMapper goodsExMapper;

	@Override
	@Transactional
	@ShowParam
	public void add(Goods goods) {
		goodsExMapper.insert(goods);
	}

	@Override
	@Transactional
	@ShowParam
	public void addBatch(List<Goods> goodsList) {
		goodsExMapper.addBatch(goodsList);
	}

	public static String topicName = "topic3";
	
	@Autowired
	KafkaTemplate<String,Object> kafkaTemplate;
	
	@Override
	@Transactional(transactionManager ="kafkaAndDBTransactionManager")
	public void transationKafkaMsg(Goods goods,Boolean throwException) throws Exception {

		// 设置事务ID
		kafkaTemplate.send( topicName ,JSONObject.toJSONString( goods ) );
		
		goodsExMapper.insert(goods);
		
		
		// producer.commitTransaction();

		if( throwException ) {
			throw new RuntimeException("故意抛出一次");
		}
		
	}

}
