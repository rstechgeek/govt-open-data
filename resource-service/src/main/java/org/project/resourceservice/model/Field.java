package org.project.resourceservice.model;

import java.io.Serializable;

public class Field implements Serializable {
    private String name;
    private String format;
    private String id;
    private String type;

    public Field() {
    }

    public Field(String name, String format, String id, String type) {
        this.name = name;
        this.format = format;
        this.id = id;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String format;
        private String id;
        private String type;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder format(String format) {
            this.format = format;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Field build() {
            return new Field(name, format, id, type);
        }
    }
}
