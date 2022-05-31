package com.lomi.kafka;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

public class KafkaProducerTest {

	public static String topicName = "topic3";

	Properties properties = new Properties();
	{
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.200:9092");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	}
	
	/**
	 * 同步发送(并且拿到消息回调)
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void sysnsend() throws InterruptedException, ExecutionException {
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

		producer.send(new ProducerRecord<String, String>(topicName, "Message", "我发送了一条同步消息"), new Callback() {

			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				System.out.println(metadata.partition());
				System.out.println(metadata.offset());
				System.out.println(metadata.topic());
				System.out.println(metadata.serializedKeySize());
				System.out.println(metadata.serializedValueSize());
			}
		}).get();

		producer.close();
	}
	
	/**
	 * 异步发送 1秒钟
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void asynsend() throws InterruptedException, ExecutionException {
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
		long start = System.currentTimeMillis();
		
		int i  = 0;
		for(;System.currentTimeMillis()-start < 1000;) {
			producer.send(new ProducerRecord<String, String>(topicName, "Message", "我发送了一条消息"+i++));
		}
		
		producer.close();
	}
	
	
	/**
	 * 发送消息的ack(最少一次，并且同步发送)
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void ack() throws InterruptedException, ExecutionException {
		//设置为最少一次
		properties.put(ProducerConfig.ACKS_CONFIG, "all");
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
		
		producer.send(new ProducerRecord<String, String>(topicName, "Message", "我发送了一条消息同步"), new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				System.out.println(metadata.partition());
				System.out.println(metadata.offset());
				System.out.println(metadata.topic());
				System.out.println(metadata.serializedKeySize());
				System.out.println(metadata.serializedValueSize());
			}
		}).get();

		producer.close();
	}
	
	
	/**
	 * 发送幂等消息(会默认开启，最少一次)
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void idempotenceMsg() throws InterruptedException, ExecutionException {
		//设置为最少一次
		properties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
		
		producer.send(new ProducerRecord<String, String>(topicName, "Message", "我发送了一条幂等消息"), new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				System.out.println(metadata.partition());
				System.out.println(metadata.offset());
				System.out.println(metadata.topic());
				System.out.println(metadata.serializedKeySize());
				System.out.println(metadata.serializedValueSize());
			}
		}).get();

		producer.close();
	}
	
	
	/**
	 * 发送有序消息
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void orderlyMsg() throws InterruptedException, ExecutionException {
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
		
		//顺序消息需要指定分区，或者key
		List<PartitionInfo> partitions = producer.partitionsFor(topicName);
		
		producer.send(new ProducerRecord<String, String>(topicName,partitions.get(0).partition(), "Message", "我发送了一条顺序消息1"), new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				System.out.println(metadata.partition());
				System.out.println(metadata.offset());
				System.out.println(metadata.topic());
				System.out.println(metadata.serializedKeySize());
				System.out.println(metadata.serializedValueSize());
			}
		}).get();
		
		producer.send(new ProducerRecord<String, String>(topicName,partitions.get(0).partition(), "Message", "我发送了一条顺序消息2"), new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				System.out.println(metadata.partition());
				System.out.println(metadata.offset());
				System.out.println(metadata.topic());
				System.out.println(metadata.serializedKeySize());
				System.out.println(metadata.serializedValueSize());
			}
		}).get();


		producer.close();
	}
	
	
	
	/**
	 * 发送幂等消息(会默认开启，最少一次)
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void transactionMsg() throws InterruptedException, ExecutionException {
		//设置事务ID
		properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transactionID1");
		KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
		
		producer.initTransactions();
		producer.beginTransaction();
		
		
		producer.send(new ProducerRecord<String, String>(topicName, "Message", "我发送了一条事务消息"), new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				System.out.println(metadata.partition());
				System.out.println(metadata.offset());
				System.out.println(metadata.topic());
				System.out.println(metadata.serializedKeySize());
				System.out.println(metadata.serializedValueSize());
			}
		}).get();
		
		
		//producer.commitTransaction();

		producer.close();
	}
	
	
	
	
	

}
