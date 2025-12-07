package org.example.godfx.model;

/**
 * Model representing an organization with resource count.
 */
public class Organization {
    private String orgName;
    private Integer resourceCount;

    public Organization() {
    }

    public Organization(String orgName, Integer resourceCount) {
        this.orgName = orgName;
        this.resourceCount = resourceCount;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(Integer resourceCount) {
        this.resourceCount = resourceCount;
    }

    @Override
    public String toString() {
        return orgName + " (" + resourceCount + ")";
    }
}
