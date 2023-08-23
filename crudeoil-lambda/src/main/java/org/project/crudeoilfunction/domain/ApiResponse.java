package org.project.crudeoilfunction.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse implements Serializable {

    private String index_name;
    private String title;
    private String desc;
    private float created;
    private float updated;
    private String created_date;
    private String updated_date;
    private String active;
    private String visualizable;
    private String catalog_uuid;
    private String source;
    private String org_type;
    List<String> org;
    List<String> sector;
    List<Field> field;
    TargetBucket targetBucket;
    private String message;
    private String version;
    private String status;
    private float total;
    private float count;
    private String limit;
    private String offset;
    List<OilRecord> records;

//    @DynamoDbPartitionKey
//    public String getIndex_name() {
//        return index_name;
//    }
}
