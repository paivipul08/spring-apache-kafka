package com.example.kafka.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.example.kafka.domain.User;

@Component
public class KafkaProducerExample {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String, User> userKafkaTemplate;
	
	private static Logger logger = LoggerFactory.getLogger(KafkaProducerExample.class);
	
	
	public void sendMessage(String message, String topicName) {
		kafkaTemplate.send(topicName, message);
	}
	
	/**
	 * To test on cli 
	 * kafka-console-consumer --bootstrap-server localhost:9092 --topic first-kafka-topic --group first-kafka-consumer-group
	 * @param topicName
	 * @param message
	 */
	public void sendMessageWithCallback(String message,String topicName) {
        
	    ListenableFuture<SendResult<String, String>> future = 
	      kafkaTemplate.send(topicName, message);
		
	    future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
	 
	        @Override
	        public void onSuccess(SendResult<String, String> result) {
	        	logger.info("Sent message : \t"+
						"Topic : {} \t Partition : {} \t Offset : {}\t Timestamp : {}"
						,result.getRecordMetadata().topic(),result.getRecordMetadata().partition(),
						result.getRecordMetadata().offset(),result.getRecordMetadata().timestamp());
	        }
	        @Override
	        public void onFailure(Throwable ex) {
	        	logger.error("Unable to send message=[ {} ] due to : {}" 
	              , message , ex.getMessage());
	        }
	    });
	}
	
	/*
	public void sendWithRoutingTemplate(Object message, String topicName) {
		 routingKafkaTemplate.send(topicName, message);
	}
	*/
	
	public void sendCustomMessage(User user, String topicName) {
		logger.info("Sending Json Serializer : {}", user);
		logger.info("--------------------------------");

		userKafkaTemplate.send(topicName, user);
	}
}
