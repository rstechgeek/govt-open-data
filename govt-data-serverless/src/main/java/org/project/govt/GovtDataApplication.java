package org.project.govt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class GovtDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(GovtDataApplication.class, args);
	}

}
