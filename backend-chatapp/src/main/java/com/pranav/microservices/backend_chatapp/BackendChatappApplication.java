package com.pranav.microservices.backend_chatapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableJpaRepositories("com.pranav.microservices.backend_chatapp.repository")
@CrossOrigin
public class BackendChatappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendChatappApplication.class, args);
	}

}
