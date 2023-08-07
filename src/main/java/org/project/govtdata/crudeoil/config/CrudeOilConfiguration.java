package org.project.govtdata.crudeoil.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.project.govtdata.common.CommonProperties;
import org.project.govtdata.crudeoil.controller.CrudeOilController;
import org.project.govtdata.crudeoil.domain.ApiResponse;
import org.project.govtdata.crudeoil.service.CrudeOilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CrudeOilConfiguration implements WebFluxConfigurer {
    @Autowired
    private CommonProperties commonProperties;
    @Autowired
    private CrudeOilProperties crudeOilProperties;

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().enableLoggingRequestDetails(true);
    }

    @Bean(name = "webClient")
    public WebClient webClient() {
        return WebClient.builder().baseUrl(commonProperties.getUrl().concat("/")
                        .concat(crudeOilProperties.getResource())
                        .concat("?api-key=")
                        .concat(commonProperties.getApiKey()))
                .build();
    }

    @Bean
    public OpenAPI crudeOilMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Crude Oil API")
                        .description("Description of Crude Oil API")
                        .version("1.0"));
    }


}
