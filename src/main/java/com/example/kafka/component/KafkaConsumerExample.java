package com.example.kafka.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.kafka.domain.User;

@Component
public class KafkaConsumerExample {
	
	private static Logger logger = LoggerFactory.getLogger(KafkaConsumerExample.class);
	
	@KafkaListener(topics = "kafka-topic-1")
	void listener(String message) {
		logger.info("Listener [{}]", message);
	}

	@KafkaListener(topics = { "kafka-topic-1", "kafka-topic-2" }, groupId = "kafka-group-2")
	void commonListenerForMultipleTopics(String message) {
		logger.info("MultipleTopicListener - [{}]", message);
	}

	@KafkaListener(topicPartitions = @TopicPartition(topic = "kafka-topic-3", partitionOffsets = {
			@PartitionOffset(partition = "0", initialOffset = "0") }), groupId = "kafka-group-3")
	void listenToParitionWithOffset(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
			@Header(KafkaHeaders.OFFSET) int offset) {
		logger.info("ListenToPartitionWithOffset [{}] from partition-{} with offset-{}", message, partition, offset);
	}
	
	@KafkaListener(topics = "user-topic-1",
			groupId="kafka-user-group",
			containerFactory="userKafkaListenerContainerFactory")
	void listener(User user) {
		logger.info("CustomUserListener [{}]", user);
	}
	
}
