package org.project.resourceservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("view_orgs")
public class OrgView {
    @Id
    @Column("org_name")
    private String orgName;

    @Column("index_ids")
    private String indexIds;

    public OrgView() {
    }

    public OrgView(String orgName, String indexIds) {
        this.orgName = orgName;
        this.indexIds = indexIds;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIndexIds() {
        return indexIds;
    }

    public void setIndexIds(String indexIds) {
        this.indexIds = indexIds;
    }
}
