package org.project.resourceservice.model;

import java.io.Serializable;

public class ApiRequest implements Serializable {
    private Integer offset;
    private Integer limit;
    private String format;
    private String resourceId;

    public ApiRequest() {
    }

    public ApiRequest(Integer offset, Integer limit, String format, String resourceId) {
        this.offset = offset;
        this.limit = limit;
        this.format = format;
        this.resourceId = resourceId;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer offset;
        private Integer limit;
        private String format;
        private String resourceId;

        public Builder offset(Integer offset) {
            this.offset = offset;
            return this;
        }

        public Builder limit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public Builder format(String format) {
            this.format = format;
            return this;
        }

        public Builder resourceId(String resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public ApiRequest build() {
            return new ApiRequest(offset, limit, format, resourceId);
        }
    }

    @Override
    public String toString() {
        return "ApiRequest{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", format='" + format + '\'' +
                ", resourceId='" + resourceId + '\'' +
                '}';
    }
}
