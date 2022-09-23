package com.example.biddingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages={"com.example.*"})
@EnableAutoConfiguration
@EnableScheduling
public class BiddingSystem {

	public static void main(String[] args) {
		SpringApplication.run(BiddingSystem.class, args);
	}

}
