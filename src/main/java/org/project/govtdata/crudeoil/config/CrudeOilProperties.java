package org.project.govtdata.crudeoil.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "crude-oil.data.gov")
public class CrudeOilProperties {

    private String resource;
}
