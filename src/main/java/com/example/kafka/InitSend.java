package com.example.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.kafka.component.KafkaProducerExample;
import com.example.kafka.domain.User;

@Component
public class InitSend {
	private static Logger logger = LoggerFactory.getLogger(KafkaProducerExample.class);
	
	@Autowired
	private KafkaProducerExample kafkaProducerExample;
	
	@Value(value = "${kafka.topic-1}")
	private String kafkaTopic1;
	
	@Value(value = "${kafka.topic-2}")
	private String kafkaTopic2;
	
	@Value(value = "${kafka.topic-3}")
	private String kafkaTopic3;
	
	@Value(value = "${kafka.topic-default}")
	private String defaultTopic;
	
	@EventListener
	void initiateSendingMessage(ApplicationReadyEvent event) throws InterruptedException {
		Thread.sleep(5000);
		logger.info("---------------------------------");
		kafkaProducerExample.sendMessage("I'll be recevied by MultipleTopicListener, Listener & ClassLevel KafkaHandler", kafkaTopic1);
		
		Thread.sleep(5000);
		logger.info("---------------------------------");
		kafkaProducerExample.sendMessage("This message will be ignored", kafkaTopic2);
		
		Thread.sleep(5000);
		logger.info("---------------------------------");
		kafkaProducerExample.sendMessage("This message will be received", kafkaTopic2);
		
		Thread.sleep(5000);
		logger.info("---------------------------------");
		kafkaProducerExample.sendMessage("I'll be received by ListenToPartitionWithOffset", kafkaTopic3);
		
		Thread.sleep(5000);
		logger.info("---------------------------------");
		kafkaProducerExample.sendMessage("A message to defalut topic", defaultTopic);
		
		
		Thread.sleep(5000);
		logger.info("---------------------------------");
		kafkaProducerExample.sendMessageWithCallback("I'll get a asyc Callback", kafkaTopic2);
		
		Thread.sleep(5000);
		logger.info("---------------------------------");
		kafkaProducerExample.sendCustomMessage(new User("vipul"), "user-topic-1");
	}

}
