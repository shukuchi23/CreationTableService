package com.example.creationtablesserver.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
	scanBasePackages = {
		"com.example.creationtablesserver.controller", "com.example.creationtablesserver.dao",
         "com.example.creationtablesserver.model"
		}
)
public class CreationtablesserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreationtablesserverApplication.class, args);
	}

}
