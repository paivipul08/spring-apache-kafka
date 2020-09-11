package com.example.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	/***
	 * A KafkaAdmin bean is responsible for creating new topics in our broker.
	 * With Spring Boot, a KafkaAdmin bean is automatically registered.
	 * For a non Spring Boot application we have to manually register KafkaAdmin bean
	 */
	/*
	@Value(value = "${kafka.bootstrap-servers}")
	private String bootstrapServer;

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		return new KafkaAdmin(configs);
	}
	*/
	
	@Value(value = "${kafka.topic-1}")
	private String kafkaTopic1;
	
	@Value(value = "${kafka.topic-2}")
	private String kafkaTopic2;
	
	@Value(value = "${kafka.topic-3}")
	private String kafkaTopic3;
	
	@Value(value = "${kafka.topic-default}")
	private String defaultTopic;
	
	@Bean
	public NewTopic topic1() {
		return TopicBuilder.name(kafkaTopic1)
				.partitions(3)
				.replicas(1)
				.build();
	}

	@Bean
	public NewTopic topic2() {
		return TopicBuilder.name(kafkaTopic2)
				.partitions(3)
				.replicas(1)
				.build();
	}
	
	@Bean
	public NewTopic topic3() {
		return TopicBuilder.name(kafkaTopic3)
				.partitions(3)
				.replicas(1)
				.build();
	}
	
	@Bean
	public NewTopic defaultTopic() {
		return TopicBuilder.name(defaultTopic)
				.partitions(3)
				.replicas(1)
				.build();
	}
	
	@Bean
	public NewTopic userTopic() {
		return TopicBuilder.name("user-topic-1")
				.partitions(3)
				.replicas(1)
				.build();
	}
}
