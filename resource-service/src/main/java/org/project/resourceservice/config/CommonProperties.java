package org.project.resourceservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix="open.data.gov")
public class CommonProperties {
    private String url;
    private String apiKey;
    private String resource;
}
