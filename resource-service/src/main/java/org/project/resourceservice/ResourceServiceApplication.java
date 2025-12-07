package org.project.resourceservice;

import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.service.RecordDetailService;
import org.project.resourceservice.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ResourceServiceApplication {
    private static final Logger log = LoggerFactory.getLogger(ResourceServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ResourceServiceApplication.class, args);
    }
}
