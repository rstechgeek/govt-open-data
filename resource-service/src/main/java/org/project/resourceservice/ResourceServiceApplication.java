package org.project.resourceservice;

import lombok.extern.slf4j.Slf4j;
import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.model.ApiResponse;
import org.project.resourceservice.model.RecordDetail;
import org.project.resourceservice.service.ResourceService;
import org.project.resourceservice.service.impl.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

@Slf4j
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
//                service.loadAllResource();
//                service.getResource(
//                        ApiRequest.builder().resourceId("c21789e6-f6c3-4178-a9a1-a6ac9e7d1abc")
//                                .limit(100).offset(1).format("json").build())
//                        .subscribe(recordDetail -> log.info(" Record " + recordDetail.toString()));
                service.getActiveResource().count().subscribe(count -> log.info("Total Active resources " + count));

            }

        };
    }

}
