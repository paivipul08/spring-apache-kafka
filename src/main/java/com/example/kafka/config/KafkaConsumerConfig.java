package com.example.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.kafka.domain.User;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

	@Value(value = "${kafka.bootstrap-servers}")
	private String bootstrapServer;
	
	@Value(value = "${kafka.consumer.group-id}")
	private String groupId;
	
	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				bootstrapServer);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
				StringDeserializer.class);
		 props.put(ConsumerConfig.GROUP_ID_CONFIG, 
				 "default-kafka-group");
		 /*
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG,
				"300000"
				);
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG,
				"10"
				);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
				"latest"
				);
		*/
		return props;
	}

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> 	kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setRecordFilterStrategy(record -> 
	      record.value().contains("ignored"));
		return factory;
	}
	
	@Bean
	public ConsumerFactory<String, User> userConsumerFactory() {
		Map<String, Object> props  = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
				bootstrapServer);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, 
				"kafka-user-group");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
				JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(User.class));
	}
	
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> userKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(userConsumerFactory());
//		factory.setMessageConverter(new StringJsonMessageConverter());
		return factory;
	}

}
