package com.example.biddingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages={"com.example.*"})
@EnableAutoConfiguration
public class BiddingSystem {

	public static void main(String[] args) {
		SpringApplication.run(BiddingSystem.class, args);
	}

}
