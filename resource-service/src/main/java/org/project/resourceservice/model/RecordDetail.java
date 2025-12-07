package org.project.resourceservice.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecordDetail implements Serializable {
    @JsonProperty("index_name")
    private String indexName;
    private String title;
    private String desc;
    private float timestamp;
    private float created;
    private float updated;
    @JsonProperty("created_date")
    private String createdDate;
    @JsonProperty("updated_date")
    private String updatedDate;
    private String active;
    private String visualizable;
    @JsonProperty("catalog_uuid")
    private String catalogUuid;
    private String source;
    private String org_type;
    private List<String> org;
    private List<String> sector;
    private List<Object> field;
    private String message;
    private String version;
    private String status;
    private float total;
    private float count;
    private String limit;
    private String offset;
    @JsonProperty("target_bucket")
    private TargetBucket target_bucket;
    private List<Object> records;

    public RecordDetail() {
    }

    public RecordDetail(String indexName, String title, String desc, float timestamp, float created, float updated,
            String createdDate, String updatedDate, String active, String visualizable, String catalogUuid,
            String source, String org_type, List<String> org, List<String> sector, List<Object> field, String message,
            String version, String status, float total, float count, String limit, String offset,
            TargetBucket target_bucket, List<Object> records) {
        this.indexName = indexName;
        this.title = title;
        this.desc = desc;
        this.timestamp = timestamp;
        this.created = created;
        this.updated = updated;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.active = active;
        this.visualizable = visualizable;
        this.catalogUuid = catalogUuid;
        this.source = source;
        this.org_type = org_type;
        this.org = org;
        this.sector = sector;
        this.field = field;
        this.message = message;
        this.version = version;
        this.status = status;
        this.total = total;
        this.count = count;
        this.limit = limit;
        this.offset = offset;
        this.target_bucket = target_bucket;
        this.records = records;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(float timestamp) {
        this.timestamp = timestamp;
    }

    public float getCreated() {
        return created;
    }

    public void setCreated(float created) {
        this.created = created;
    }

    public float getUpdated() {
        return updated;
    }

    public void setUpdated(float updated) {
        this.updated = updated;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getVisualizable() {
        return visualizable;
    }

    public void setVisualizable(String visualizable) {
        this.visualizable = visualizable;
    }

    public String getCatalogUuid() {
        return catalogUuid;
    }

    public void setCatalogUuid(String catalogUuid) {
        this.catalogUuid = catalogUuid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOrg_type() {
        return org_type;
    }

    public void setOrg_type(String org_type) {
        this.org_type = org_type;
    }

    public List<String> getOrg() {
        return org;
    }

    public void setOrg(List<String> org) {
        this.org = org;
    }

    public List<String> getSector() {
        return sector;
    }

    public void setSector(List<String> sector) {
        this.sector = sector;
    }

    public List<Object> getField() {
        return field;
    }

    public void setField(List<Object> field) {
        this.field = field;
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

    public TargetBucket getTarget_bucket() {
        return target_bucket;
    }

    public void setTarget_bucket(TargetBucket target_bucket) {
        this.target_bucket = target_bucket;
    }

    public List<Object> getRecords() {
        return records;
    }

    public void setRecords(List<Object> records) {
        this.records = records;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String indexName;
        private String title;
        private String desc;
        private float timestamp;
        private float created;
        private float updated;
        private String createdDate;
        private String updatedDate;
        private String active;
        private String visualizable;
        private String catalogUuid;
        private String source;
        private String org_type;
        private List<String> org;
        private List<String> sector;
        private List<Object> field;
        private String message;
        private String version;
        private String status;
        private float total;
        private float count;
        private String limit;
        private String offset;
        private TargetBucket target_bucket;
        private List<Object> records;

        public Builder indexName(String indexName) {
            this.indexName = indexName;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder timestamp(float timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder created(float created) {
            this.created = created;
            return this;
        }

        public Builder updated(float updated) {
            this.updated = updated;
            return this;
        }

        public Builder createdDate(String createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder updatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
            return this;
        }

        public Builder active(String active) {
            this.active = active;
            return this;
        }

        public Builder visualizable(String visualizable) {
            this.visualizable = visualizable;
            return this;
        }

        public Builder catalogUuid(String catalogUuid) {
            this.catalogUuid = catalogUuid;
            return this;
        }

        public Builder source(String source) {
            this.source = source;
            return this;
        }

        public Builder org_type(String org_type) {
            this.org_type = org_type;
            return this;
        }

        public Builder org(List<String> org) {
            this.org = org;
            return this;
        }

        public Builder sector(List<String> sector) {
            this.sector = sector;
            return this;
        }

        public Builder field(List<Object> field) {
            this.field = field;
            return this;
        }

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

        public Builder target_bucket(TargetBucket target_bucket) {
            this.target_bucket = target_bucket;
            return this;
        }

        public Builder records(List<Object> records) {
            this.records = records;
            return this;
        }

        public RecordDetail build() {
            return new RecordDetail(indexName, title, desc, timestamp, created, updated, createdDate, updatedDate,
                    active, visualizable, catalogUuid, source, org_type, org, sector, field, message, version, status,
                    total, count, limit, offset, target_bucket, records);
        }
    }
}
