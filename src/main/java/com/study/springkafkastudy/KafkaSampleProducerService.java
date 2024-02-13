package com.study.springkafkastudy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSampleProducerService {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void sendMessage(String message) {
		System.out.println("send message : " + message);
		this.kafkaTemplate.send("oingdaddy", message);
	}
}