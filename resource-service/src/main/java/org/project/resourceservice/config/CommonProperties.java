package org.project.resourceservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "open.data.gov")
public class CommonProperties {
    private String url;
    private String apikey;
    private String resource;
}
