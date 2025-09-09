package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ProjectReviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectReviewApplication.class, args);
	}

	 @Bean(value = "restTemplate")
	   RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }
}
