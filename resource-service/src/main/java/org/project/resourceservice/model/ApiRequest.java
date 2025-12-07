package org.project.resourceservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequest implements Serializable {
    private Integer offset;
    private Integer limit;
    private String format;
    private String resourceId;
}
