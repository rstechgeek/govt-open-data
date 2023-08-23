package org.project.resourceservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Objects;

@Data
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resource implements Persistable<String> {
    @Id
    private String index_id;
    private String record;

    @Override
    public String getId() {
        return index_id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return true;
    }
}
