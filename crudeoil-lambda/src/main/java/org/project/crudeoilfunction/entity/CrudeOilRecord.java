package org.project.crudeoilfunction.entity;

import org.project.crudeoilfunction.domain.TargetBucket;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.ArrayList;

//@DynamoDbBean
public class CrudeOilRecord {
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
    ArrayList<Object> org;
    ArrayList<Object> sector;
    ArrayList<Object> field;
    TargetBucket targetBucket;
    private String message;
    private String version;
    private String status;
    private float total;
    private float count;
    private String limit;
    private String offset;
    ArrayList<Object> records;

//    @DynamoDbPartitionKey
//    public String getIndex_name() {
//        return index_name;
//    }
}
