package org.project.govt.crudeoil.config;

//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CrudeOilConfiguration implements WebFluxConfigurer {
    @Autowired
    private CrudeOilProperties crudeOilProperties;

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().enableLoggingRequestDetails(true);
    }

    @Bean(name = "webClient")
    public WebClient webClient() {
        return WebClient.builder().baseUrl(crudeOilProperties.getUrl().concat("/")
                        .concat(crudeOilProperties.getResource())
                        .concat("?api-key=")
                        .concat(crudeOilProperties.getApiKey()))
                .build();
    }

//    @Bean
//    public OpenAPI crudeOilMicroserviceOpenAPI() {
//        return new OpenAPI()
//                .info(new Info().title("Crude Oil API")
//                        .description("Description of Crude Oil API")
//                        .version("1.0"));
//    }


}
