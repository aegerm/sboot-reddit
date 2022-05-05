package com.egersistemasavancados.sbootreddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SbootRedditApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbootRedditApplication.class, args);
	}
}
