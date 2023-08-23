package org.project.crudeoilfunction.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.io.Serializable;

@Data
@Builder
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
public class OilRecord implements Serializable {
    @JsonIgnore
    private String id;
    private String month;
    private String year;
    @JsonProperty("oil_companies_")
    private String oilCompanies;
    @JsonProperty("quantity_000_metric_tonnes_")
    private String quantityMetricTonnes;
    @JsonProperty("last_updated")
    private String lastUpdated;

    @DynamoDbSortKey
    public String getOilCompanies() {
        return oilCompanies;
    }

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }
}
