package org.project.resourceservice.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordDetail implements Serializable {
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
    private List<String> org;
    private List<String> sector;
    private List<Object> field;
    private String message;
    private String version;
    private String status;
    private float total;
    private float count;
    private String limit;
    private String offset;
    @JsonProperty("target_bucket")
    private TargetBucket target_bucket;
    private List<Object> records;

}
