package com.skettle.record_shop_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan({"com.skettle.record_shop_api"})
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class RecordShopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordShopApiApplication.class, args);
	}

}
