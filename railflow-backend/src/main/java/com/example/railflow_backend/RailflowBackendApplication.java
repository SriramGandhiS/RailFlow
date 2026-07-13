package com.example.railflow_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(excludeName = {
		"org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
})
public class RailflowBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailflowBackendApplication.class, args);
	}

}
