package com.example.kafka.demo;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Configuration
public class ProducerDemo {
	
	private static Logger logger = LoggerFactory.getLogger(ProducerDemo.class);

	/**
	 * Simple kakfa producer
	 */
//	@Bean
	public void producer() {
		System.out.println("Creating a producer");
		//create producer properties
		Properties properties=new Properties();
		//Traditional way of adding properties
		/*
		properties.setProperty("bootstrap.servers", "127.0.0.1:9092");
		properties.setProperty("key.serializer", StringSerializer.class.getName());
		properties.setProperty("value.serializer", StringSerializer.class.getName());
		*/
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		//create producer
		KafkaProducer<String, String> kafkaProducer =new KafkaProducer<String, String>(properties);
		
		//create producer record
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("first_topic", "hello world");
		
		//send data - asynchronous
		kafkaProducer.send(producerRecord);
		
		kafkaProducer.flush();
		kafkaProducer.close();
	}
	
	/**
	 * A producer with implementation of callback -OnCompletion
	 * In this case each record will go to different partitions 
	 * to view the records run the command
	 * kafka-console-consumer --bootstrap-server localhost:9092 --topic first_topic --group my-third-application
	 */
//	@Bean
	public void producerCallback() {
		logger.info("Creating a Producer with Callback");
		//create producer properties
		Properties properties=new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		//create producer
		KafkaProducer<String, String> kafkaProducer =new KafkaProducer<String, String>(properties);
		
		//create producer record
		for(int i=0;i<10;i++) {
			ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("first_topic", "hello world "+i);

			//send data - asynchronous
			kafkaProducer.send(producerRecord,new Callback() {
				//executes every time a record is successfully sent or an exception is thrown
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if(exception == null) {
						logger.info("Recieved new record metadata : \t"+
								"Topic : {} \t Partition : {} \t Offset : {}\t Timestamp : {}"
								,metadata.topic(),metadata.partition(),metadata.offset(),metadata.timestamp());
					}else {
						logger.error("Error producing record : {}",exception.getMessage());
					}

				}
			});
			kafkaProducer.flush();
		}
		kafkaProducer.close();
	}

	/**
	 * Creating a producer with key
	 * This gaurantees that the value with the same key shall always go on to the same partition
	 */
//	@Bean
	public void producerKey() {
		logger.info("Creating a Producer with Key");
		//create producer properties
		Properties properties=new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		
		//create producer
		KafkaProducer<String, String> kafkaProducer =new KafkaProducer<String, String>(properties);
		
		//create producer record
		for(int i=0;i<10;i++) {
			String topic = "first_topic";
			String value = "hello world "+i;
			String key = "id_"+i;
			ProducerRecord<String, String> producerRecord = 
					new ProducerRecord<String, String>(topic, key,value);
			logger.info("Key : {}",key);
			//send data - asynchronous
			kafkaProducer.send(producerRecord,new Callback() {
				//executes every time a record is successfully sent or an exception is thrown
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if(exception == null) {
						logger.info("Recieved new record metadata : \t"+
								"Topic : {} \t Partition : {} \t Offset : {}\t Timestamp : {}"
								,metadata.topic(),metadata.partition(),metadata.offset(),metadata.timestamp());
					}else {
						logger.error("Error producing record : {}",exception.getMessage());
					}

				}
			});
			kafkaProducer.flush();
		}
		kafkaProducer.close();
	}
}
