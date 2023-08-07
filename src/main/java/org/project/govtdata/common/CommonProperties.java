package org.project.govtdata.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Data
@Configuration
@ConfigurationProperties(prefix = "common")
public class CommonProperties {
    private String url;
    private String apiKey;
}
