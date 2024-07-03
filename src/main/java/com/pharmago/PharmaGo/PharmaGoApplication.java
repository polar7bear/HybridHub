package com.pharmago.PharmaGo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PharmaGoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmaGoApplication.class, args);
	}

}
