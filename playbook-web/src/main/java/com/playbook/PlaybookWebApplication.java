package com.playbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.playbook.client"})
public class PlaybookWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaybookWebApplication.class, args);
	}

}
