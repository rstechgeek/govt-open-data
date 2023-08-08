package org.project.crudeoil.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Builder
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
    ArrayList<Object> org = new ArrayList<>();
    ArrayList<Object> sector = new ArrayList<>();
    ArrayList<Object> field = new ArrayList<>();
    TargetBucket targetBucket;
    private String message;
    private String version;
    private String status;
    private float total;
    private float count;
    private String limit;
    private String offset;
    ArrayList<Object> records = new ArrayList<>();

}
