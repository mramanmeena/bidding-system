package com.example.biddingsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = {"com.example.biddingsystem.services.AuctionService"})
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

}
