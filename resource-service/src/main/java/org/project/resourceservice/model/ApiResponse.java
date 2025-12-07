package org.project.resourceservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public ApiResponse() {
    }

    public ApiResponse(String message, String version, String status, float total, float count, String limit,
            String offset, List<RecordDetail> recordDetails, Object params) {
        this.message = message;
        this.version = version;
        this.status = status;
        this.total = total;
        this.count = count;
        this.limit = limit;
        this.offset = offset;
        this.recordDetails = recordDetails;
        this.params = params;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public List<RecordDetail> getRecordDetails() {
        return recordDetails;
    }

    public void setRecordDetails(List<RecordDetail> recordDetails) {
        this.recordDetails = recordDetails;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String message;
        private String version;
        private String status;
        private float total;
        private float count;
        private String limit;
        private String offset;
        private List<RecordDetail> recordDetails;
        private Object params;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder total(float total) {
            this.total = total;
            return this;
        }

        public Builder count(float count) {
            this.count = count;
            return this;
        }

        public Builder limit(String limit) {
            this.limit = limit;
            return this;
        }

        public Builder offset(String offset) {
            this.offset = offset;
            return this;
        }

        public Builder recordDetails(List<RecordDetail> recordDetails) {
            this.recordDetails = recordDetails;
            return this;
        }

        public Builder params(Object params) {
            this.params = params;
            return this;
        }

        public ApiResponse build() {
            return new ApiResponse(message, version, status, total, count, limit, offset, recordDetails, params);
        }
    }
}
