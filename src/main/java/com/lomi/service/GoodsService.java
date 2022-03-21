package com.lomi.service;

import java.util.List;

import com.lomi.entity.Goods;

public interface GoodsService {

	void add(Goods goods);

	void addBatch(List<Goods> goodsList);
	
}
