package org.project.commonservice.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor 
public class CommonRequest implements Serializable {
    private Integer offset;
    private Integer limit;
    private String format;
}
