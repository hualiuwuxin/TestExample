package com.lomi.zookeeper;

import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

public class ZookeeperCuratorTest {
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
	public void create() throws Exception{
		CuratorFramework client = CuratorFrameworkFactory.builder()
				.connectString("127.0.0.1:2181") // zk服务器地址
				.sessionTimeoutMs(1000 * 60) // 心跳检测时间周期(毫秒)
				.connectionTimeoutMs(5000) // 连接超时时间
				// 重试策略
				// ExponentialBackoffRetry:重试指定的次数, 且每一次重试之间停顿的时间逐渐增加.
				// RetryNTimes:指定最大重试次数的重试策略
				// RetryOneTime:仅重试一次
				// RetryUntilElapsed:一直重试直到达到规定的时间
				.retryPolicy(new ExponentialBackoffRetry(1000, 3))
				// 带上验证口令（访问权限节点时需要）
				.authorization("digest", "user:123456".getBytes())
				// 表示该客户只再/aclnodes下操作
				.namespace("aclnodes").build();
		
		
		// 启动
		client.start();
		client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/curator/data","test".getBytes());
	}

}
