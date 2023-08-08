package org.project.crudeoil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class CrudeoilApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudeoilApplication.class, args);
	}

}
