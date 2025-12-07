package org.project.resourceservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private String version;
    private String status;
    private float total;
    private float count;
    private String limit;
    private String offset;
    @JsonProperty(value = "records")
    private List<RecordDetail> recordDetails;
    private Object params;
}
