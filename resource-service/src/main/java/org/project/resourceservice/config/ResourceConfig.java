package org.project.resourceservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
//@EnableTransactionManagement
@EnableR2dbcRepositories(basePackages = {"org.project.resourceservice.repository"})
public class ResourceConfig implements WebFluxConfigurer {
    @Autowired
    private CommonProperties crudeOilProperties;

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().enableLoggingRequestDetails(true);
    }

    @Bean(name = "webClient")
    public WebClient webClient() {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("myConnectionPool")
                .maxConnections(50000)
                .pendingAcquireMaxCount(50000)
                .maxIdleTime(Duration.ofSeconds(60))
                .build();
        ReactorClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(HttpClient.create(connectionProvider));
        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        return WebClient.builder().exchangeStrategies(strategies).clientConnector(clientHttpConnector).baseUrl(crudeOilProperties.getUrl())
                .build();
    }

    // @Bean
    // public OpenAPI crudeOilMicroserviceOpenAPI() {
    // return new OpenAPI()
    // .info(new Info().title("Resource Oil API")
    // .description("Description of Crude Oil API")
    // .version("1.0"));
    // }
}
