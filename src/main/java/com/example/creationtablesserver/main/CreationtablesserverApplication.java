package com.example.creationtablesserver.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
 scanBasePackages = {
  "com.example.creationtablesserver.controller", "com.example.creationtablesserver.dao",
  "com.example.creationtablesserver.service"
 }


)
@EnableJpaRepositories(
 basePackages = "com.example.creationtablesserver.repository"
)
@EntityScan(basePackages = {"com.example.creationtablesserver.model.META"})
public class CreationtablesserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreationtablesserverApplication.class, args);
	}

}
