package org.project.commonservice.pojo;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceResponse implements Serializable {
    private String message;
    private String version;
    private String status;
    private float total;
    private float count;
    private String limit;
    private String offset;
    ArrayList<Record> records;
    Object params;
}
