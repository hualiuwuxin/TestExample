package com.lomi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lomi.entity.Goods;
import com.lomi.mapper.GoodsExMapper;
import com.lomi.service.GoodsService;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	GoodsExMapper goodsExMapper;
	
	@Override
	@Transactional
	public void add(Goods goods) {
		goodsExMapper.insert(goods);
	}

	@Override
	@Transactional
	public void addBatch(List<Goods> goodsList) {
		goodsExMapper.addBatch(goodsList);
	}

}
