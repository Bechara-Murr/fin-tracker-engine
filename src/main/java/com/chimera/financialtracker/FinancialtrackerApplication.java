package com.chimera.financialtracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinancialtrackerApplication {

	@Autowired
	private Config config;

	public static void main(String[] args) {
		SpringApplication.run(FinancialtrackerApplication.class, args);
	}

}
