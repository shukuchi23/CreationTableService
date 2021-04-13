package com.example.creationtablesserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
 scanBasePackages = {"com.example.creationtablesserver"}


)
@EnableJpaRepositories(
 basePackages = "com.example.creationtablesserver.repository"
)
/*scanBasePackages = {
         "com.example.creationtablesserver.rest", "com.example.creationtablesserver.dao",
  "com.example.creationtablesserver.service", "com.example.creationtablesserver.config",
		 "com.example.creationtablesserver.*"
 }*/
@EntityScan(basePackages = {"com.example.creationtablesserver.model"})
public class CreationtablesserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreationtablesserverApplication.class, args);
	}

}
