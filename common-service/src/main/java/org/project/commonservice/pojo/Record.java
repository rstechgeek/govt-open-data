package org.project.commonservice.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Record implements Serializable {
    @JsonProperty("index_name")
    private String indexName;
    private String title;
    private String desc;
    private float timestamp;
    private float created;
    private float updated;
    @JsonProperty("created_date")
    private String createdDate;
    @JsonProperty("updated_date")
    private String updatedDate;
    private String active;
    private String visualizable;
    @JsonProperty("catalog_uuid")
    private String catalogUuid;
    private String source;
    private String org_type;
    ArrayList<Object> org;
    ArrayList<Object> sector;
    ArrayList<Object> field;
    @JsonProperty("target_bucket")
    TargetBucket target_bucket;

}
