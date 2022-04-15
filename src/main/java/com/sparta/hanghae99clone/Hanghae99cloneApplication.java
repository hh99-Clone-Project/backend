package com.sparta.hanghae99clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Hanghae99cloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hanghae99cloneApplication.class, args);
	}

}