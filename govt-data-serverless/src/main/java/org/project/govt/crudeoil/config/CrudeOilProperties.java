package org.project.govt.crudeoil.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "crude-oil.data.gov")
public class CrudeOilProperties {
    private String url;
    private String apiKey;
    private String resource;
}
