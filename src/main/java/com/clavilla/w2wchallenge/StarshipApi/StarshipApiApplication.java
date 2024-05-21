package com.clavilla.w2wchallenge.StarshipApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StarshipApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarshipApiApplication.class, args);
	}

}
