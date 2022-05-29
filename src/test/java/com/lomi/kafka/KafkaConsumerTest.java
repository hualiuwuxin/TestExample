package com.lomi.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

public class KafkaConsumerTest {

	String GROUPID = "groupE";

	Properties properties = new Properties();
	{
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.200:9092");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
	}
	
	
	/**
	 * 同步确认消息
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void AUTO_COMMIT() throws InterruptedException, ExecutionException {

		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.200:9092");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUPID);
		
		//自动(效率最高)
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		//自动确认时间
		properties.put("auto.commit.interval.ms", "1000");
		
		properties.put("session.timeout.ms", "30000");
		properties.put("auto.offset.reset", "latest");

		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		consumer.subscribe(Arrays.asList(KafkaProducerTest.topicName));

		int messageNo = 1;
		System.out.println("---------开始消费---------");
		
		for (;;) {
			ConsumerRecords<String, String> msgList = consumer.poll(Duration.ofMillis(200));
			
			for (ConsumerRecord<String, String> record : msgList) {
				System.out.println(messageNo + "=======receive: key = " + record.key() + ", value = "+ record.value() + " offset===" + record.offset());
				messageNo++;
			}
			
		}
		
		//consumer.close();
	}
	

	/**
	 * 同步确认消息
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void commitSync() throws InterruptedException, ExecutionException {

		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.200:9092");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUPID);
		
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		properties.put("auto.commit.interval.ms", "1000");
		properties.put("session.timeout.ms", "30000");
		properties.put("auto.offset.reset", "latest");

		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		consumer.subscribe(Arrays.asList(KafkaProducerTest.topicName));

		int messageNo = 1;
		System.out.println("---------开始消费---------");
		
		for (;;) {
			ConsumerRecords<String, String> msgList = consumer.poll(Duration.ofMillis(200));
			
			for (ConsumerRecord<String, String> record : msgList) {
				System.out.println(messageNo + "=======receive: key = " + record.key() + ", value = "+ record.value() + " offset===" + record.offset());
				messageNo++;
			}
			consumer.commitSync();
			
		}
		
		//consumer.close();
	}
	
	
	/**
	 * 异步确认消息
	 * 
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@Test
	public void commitAsync() throws InterruptedException, ExecutionException {

		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.200:9092");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUPID);
		
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
		properties.put("auto.commit.interval.ms", "1000");
		properties.put("session.timeout.ms", "30000");
		properties.put("auto.offset.reset", "latest");

		KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
		consumer.subscribe(Arrays.asList(KafkaProducerTest.topicName));

		int messageNo = 1;
		System.out.println("---------开始消费---------");
		
		for (;;) {
			ConsumerRecords<String, String> msgList = consumer.poll(Duration.ofMillis(200));
			for (ConsumerRecord<String, String> record : msgList) {
				System.out.println(messageNo + "=======receive: key = " + record.key() + ", value = "+ record.value() + " offset===" + record.offset());
				messageNo++;
			}
			consumer.commitAsync(new OffsetCommitCallback() {
				@Override
				public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
					System.out.println("异步确认");
				}
			});
		}
		
		//consumer.close();
	}

}
