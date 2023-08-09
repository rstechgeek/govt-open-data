package org.project.crudeoilfunction.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TargetBucket implements Serializable {

    private String index;
    private String type;
    private String field;

}
