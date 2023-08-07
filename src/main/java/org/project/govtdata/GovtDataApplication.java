package org.project.govtdata;

import lombok.extern.slf4j.Slf4j;
import org.project.govtdata.crudeoil.domain.ApiRequest;
import org.project.govtdata.crudeoil.domain.ApiResponse;
import org.project.govtdata.crudeoil.service.CrudeOilService;
import org.project.govtdata.crudeoil.service.impl.CrudeOilServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@SpringBootApplication
@EnableWebFlux
public class GovtDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(GovtDataApplication.class, args);
    }

//    @Bean
//    public static CommandLineRunner commandLineRunner(WebClient client) {
//        return (strings) -> {
//            CrudeOilService service = new CrudeOilServiceImpl(client);
//            ApiResponse response = service.getCrudeOilData(ApiRequest.builder().offset(0).limit(100).format("json").build()).block();
//            log.info(response.toString());
//        };
//    }

}
