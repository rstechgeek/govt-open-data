package org.project.resourceservice;

import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.model.ApiResponse;
import org.project.resourceservice.service.ResourceService;
import org.project.resourceservice.service.impl.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ResourceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return new CommandLineRunner() {
            @Autowired
            private ResourceService service;

            @Override
            public void run(String... args) throws Exception {
                // ApiResponse response = service.getResources(ApiRequest.builder().format("json").limit(10).offset(0).build());
                service.loadAllResource();
                // System.out.println(response.toString());
//                System.exit(0);
            }

        };
    }

}
