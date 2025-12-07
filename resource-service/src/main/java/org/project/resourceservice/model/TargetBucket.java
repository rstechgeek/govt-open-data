package org.project.resourceservice.model;

import java.io.Serializable;

public class TargetBucket implements Serializable {
    private String index;
    private String type;
    private String field;

    public TargetBucket() {
    }

    public TargetBucket(String index, String type, String field) {
        this.index = index;
        this.type = type;
        this.field = field;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String index;
        private String type;
        private String field;

        public Builder index(String index) {
            this.index = index;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder field(String field) {
            this.field = field;
            return this;
        }

        public TargetBucket build() {
            return new TargetBucket(index, type, field);
        }
    }
}
