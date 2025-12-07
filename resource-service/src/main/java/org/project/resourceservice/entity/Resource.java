package org.project.resourceservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.beans.Transient;

import io.r2dbc.postgresql.codec.Json;

@Table
public class Resource implements Persistable<String> {
    @Id
    private String index_id;
    @org.springframework.data.relational.core.mapping.Column("records")
    private Json records;

    public Resource() {
    }

    public Resource(String index_id, Json records) {
        this.index_id = index_id;
        this.records = records;
    }

    public String getIndex_id() {
        return index_id;
    }

    public void setIndex_id(String index_id) {
        this.index_id = index_id;
    }

    public Json getRecords() {
        return records;
    }

    public void setRecords(Json records) {
        this.records = records;
    }

    @Override
    public String getId() {
        return index_id;
    }

    @Override
    @Transient
    public boolean isNew() {
        return true;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String index_id;
        private Json records;

        public Builder index_id(String index_id) {
            this.index_id = index_id;
            return this;
        }

        public Builder records(Json records) {
            this.records = records;
            return this;
        }

        public Resource build() {
            return new Resource(index_id, records);
        }
    }
}
