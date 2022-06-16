package com.lomi.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.AddWatchMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Transaction;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

public class ZookeeperTest {
	String zookeeperAddress = "127.0.0.1:2181";
	
	ZooKeeper zooKeeper = null;
	
	@Before
	public void before() throws IOException {
		ZooKeeper zooKeeper = new ZooKeeper(zookeeperAddress, 60*1000, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				System.out.println("连接");
			}
		});
		
		this.zooKeeper = zooKeeper;
		
		System.out.println( "连接状态状态：" + zooKeeper.getState() );
	}
	
	
	/**
	 * 原生客户端
	 *
	 * @author ZHANGYUKUN
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 * @Date 2022年6月1日
	 *
	 */
	@Test
	public void create() throws IOException, KeeperException, InterruptedException {
		zooKeeper.create("/test/te/a","value".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		
	}
	
	
	/**
	 * 原生客户端
	 *
	 * @author ZHANGYUKUN
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 * @Date 2022年6月1日
	 *
	 */
	@Test
	public void getData() throws IOException, KeeperException, InterruptedException {
		Stat stat = new Stat();
		
		zooKeeper.register( new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				System.out.println("register");
				System.out.println( event.getPath() );
				System.out.println( event.getPath() );
			}
		} );
		
		zooKeeper.addWatch("/test/te/a", new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				System.out.println("addWatch");
				System.out.println( event.getPath() );
				System.out.println( event.getPath() );
			}
		}, AddWatchMode.PERSISTENT_RECURSIVE);
		
		
		byte[] rt = zooKeeper.getData("/test/te/a", new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				System.out.println("修改");
				System.out.println( event.getPath() );
				System.out.println( event.getPath() );
			}
		},stat);
		
		System.out.println( "属性"+JSONObject.toJSONString( stat ) );
		System.out.println( "值"+ new String(rt) );
		
		
		Thread.sleep(1000L*100);
		
		Transaction  transaction = zooKeeper.transaction();
		
	}

}
