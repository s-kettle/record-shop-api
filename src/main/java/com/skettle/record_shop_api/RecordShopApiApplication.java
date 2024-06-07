package com.skettle.record_shop_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.skettle.record_shop_api"})
@SpringBootApplication
public class RecordShopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordShopApiApplication.class, args);
	}

}
