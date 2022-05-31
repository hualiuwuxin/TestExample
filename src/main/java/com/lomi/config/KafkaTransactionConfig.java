package com.lomi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.kafka.transaction.KafkaTransactionManager;


/**
 * 开启kafka 的事务消息，会导致 spring 的 事务管理员不初始化，全局都没有事务了，这个类特殊处理这个情况
 * 
 * @author ZHANGYUKUN
 * @date 2022年5月29日 下午6:53:50
 *
 */

@Configuration
public class KafkaTransactionConfig {
	private final DataSource dataSource;

	private final TransactionManagerCustomizers transactionManagerCustomizers;

	KafkaTransactionConfig(DataSource dataSource,ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
		this.dataSource = dataSource;
		this.transactionManagerCustomizers = transactionManagerCustomizers.getIfAvailable();
	}

	
	/**
	 * 导入kafka 事务消息以后原始 DataSourceTransactionManager 失效了，我们给它手动初始化一个
	 * @param properties
	 * @return
	 */
	@Bean
	@Primary
	public DataSourceTransactionManager transactionManager(DataSourceProperties properties) {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(this.dataSource);
		if (this.transactionManagerCustomizers != null) {
			this.transactionManagerCustomizers.customize(transactionManager);
		}
		return transactionManager;
	}
	
	
	
	/**
	 * 定义一个兼容kafka和DB 事务的  事务管理员
	 * @param transactionManager
	 * @param kafkaTransactionManager
	 * @return
	 */

	@Bean
	public ChainedTransactionManager kafkaAndDBTransactionManager(DataSourceTransactionManager transactionManager,
			KafkaTransactionManager<?, ?> kafkaTransactionManager) {
		return new ChainedTransactionManager(transactionManager, kafkaTransactionManager);

	}
	 
}