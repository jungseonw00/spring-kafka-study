package com.study.springkafkastudy.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

	// config 속성들은 많이 있습니다. 찾아보시는 것이 좋습니다.
	// 찾아보신다면 본인의 서비스와 어울리는 설정을 할 수 있을겁니다.
	@Bean
	public ConsumerFactory<String, DemoViewDTO> consumerFactory() {

		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "foo-1");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		// 들어오는 record 를 객체로 받기 위한 deserializer
		JsonDeserializer<DemoViewDTO> deserializer = new JsonDeserializer<>(DemoViewDTO.class,
			false);

		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, DemoViewDTO> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, DemoViewDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	// 수신하는 consumer 에서 record를 필터링할 수 있습니다.
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, DemoViewDTO> filterListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, DemoViewDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setRecordFilterStrategy(record -> Integer.parseInt(record.value().getAge()) > 30);
		return factory;
	}

}