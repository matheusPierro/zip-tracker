package com.matheuspierro.zip_tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ZipTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipTrackerApplication.class, args);
	}

}
