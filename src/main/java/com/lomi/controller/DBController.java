package com.lomi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lomi.annotation.ShowParam;
import com.lomi.entity.Goods;
import com.lomi.entity.in.BatchIn;
import com.lomi.entity.in.ExecuteIn;
import com.lomi.entity.in.MultiThreadIn;
import com.lomi.service.GoodsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags="数据库测试")
@RestController
@RequestMapping(value = "/db")
public class DBController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DBController.class);

	@Autowired
	GoodsService goodsService;
	
	
	
	@ApiOperation(value = "单线程插入，1W条，简单数据，耗时月26秒",notes="TPS约400")
	@RequestMapping(value="insert/singleThread", method= {RequestMethod.GET})
	@ShowParam
	public String singleThread(ExecuteIn in) throws Exception{
		
		for(int i= 0;i<in.getExecuteCount();i++) {
			goodsService.add( Goods.randomGoods() );
		}
		
		return "OK";
	}
	
	
	@ApiOperation(value="多线程(4核8线CPU,设置8线程),1W条简单数据，耗时6.5秒",notes="TPS约1500")
	@RequestMapping(value="insert/multiThread", method= {RequestMethod.GET})
	public String multiThread(MultiThreadIn in) throws Exception{
		int n = in.getExecuteCount();
		final CountDownLatch endGate = new CountDownLatch(n);
		executorService = Executors.newFixedThreadPool( in.getThreadCount() );
		
		for(int i= 0;i<n;i++) {
			executorService.execute( ()->{
				goodsService.add(Goods.randomGoods());
				endGate.countDown();
			} );
		}
		
		endGate.await();
		return "OK";
	}
	
	
	/**
	 * 
	 * 批量10个,36秒
	 * 批量50个,8.8秒
	 * 批量100,6.3秒
	 * 批量200,4.7秒
	 * 批量400,3.9秒
	 * 批量800,3.6秒
	 * 批量1600,3.5秒
	 * 批量3200,3.4秒
	 * 
	 * 批量200以内提升明显，在800以上几乎没有效率的提升
	 * @param in
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "单线程,800批量插入，10W条，简单数据，耗时月4秒",notes="TPS约2.5W")
	@RequestMapping(value="insert/singleThreadBatch", method= {RequestMethod.GET})
	public String singleThreadBatch(BatchIn in) throws Exception{
		int batchCount = in.getBatchCount();
		
		List<Goods> goodsList = new ArrayList<>();
		for(int i= 1;i<=in.getExecuteCount();i++) {
			goodsList.add( Goods.randomGoods() );
			
			if(i != 0 &&  i%batchCount==0 ) {
				goodsService.addBatch( goodsList );
				goodsList.clear();
			}
		}
		if( !goodsList.isEmpty() ) {
			goodsService.addBatch( goodsList );
			goodsList.clear();
		}
		
		return "OK";
	}
	

	@ApiOperation(value = "多线程(4核8线CPU,设置8线程),800批量插入，10W条，简单数据，耗时月2秒",notes="TPS约5W")
	@RequestMapping(value="insert/multiThreadBatch", method= {RequestMethod.GET})
	public String multiThreadBatch(BatchIn in) throws Exception{
		int batchCount = in.getBatchCount();
		int n = in.getExecuteCount();
		final CountDownLatch endGate = new CountDownLatch( (int)Math.ceil( Double.valueOf( n )/batchCount )   );
		executorService = Executors.newFixedThreadPool( in.getThreadCount() );
		
		
		
		List<Goods> goodsList = new ArrayList<>();
		for(int i= 1;i<=n;i++) {
			goodsList.add( Goods.randomGoods() );
			if(i != 0 &&  i%batchCount==0 ) {
				MyT myT = new MyT(goodsList, goodsService, endGate);
				executorService.execute( (   )->{
					myT.run();
				} );
				goodsList.clear();
			}
		}
		
		if( !goodsList.isEmpty() ) {
			MyT myT = new MyT(goodsList, goodsService, endGate);
			executorService.execute( (   )->{
				myT.run();
			} );
			goodsList.clear();
		}
		
		endGate.await();
		return "OK";
	}
	
	class MyT implements Runnable{
		private List<Goods> items;
		private GoodsService goodsService;
		private CountDownLatch endGate;
		

		public MyT(List<Goods> items, GoodsService goodsService, CountDownLatch endGate) {
			super();
			this.items = new ArrayList<>(items);
			this.goodsService = goodsService;
			this.endGate = endGate;
		}

		public List<Goods> getItems() {
			return items;
		}

		public void setItems(List<Goods> items) {
			this.items = new ArrayList<>(items);
		}

		public GoodsService getGoodsService() {
			return goodsService;
		}

		public void setGoodsService(GoodsService goodsService) {
			this.goodsService = goodsService;
		}

		public CountDownLatch getEndGate() {
			return endGate;
		}

		public void setEndGate(CountDownLatch endGate) {
			this.endGate = endGate;
		}
		

		@Override
		public void run() {
			goodsService.addBatch( items );
			endGate.countDown();
		}
		
	}
	
	
	

	
}
