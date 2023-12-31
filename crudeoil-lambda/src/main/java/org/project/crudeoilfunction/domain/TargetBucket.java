package org.project.crudeoilfunction.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class TargetBucket implements Serializable {

    private String index;
    private String type;
    private String field;

}
