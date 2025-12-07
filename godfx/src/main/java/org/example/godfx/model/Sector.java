package org.example.godfx.model;

/**
 * Model representing a sector with resource count.
 */
public class Sector {
    private String sectorName;
    private Integer resourceCount;

    public Sector() {
    }

    public Sector(String sectorName, Integer resourceCount) {
        this.sectorName = sectorName;
        this.resourceCount = resourceCount;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public Integer getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(Integer resourceCount) {
        this.resourceCount = resourceCount;
    }

    @Override
    public String toString() {
        return sectorName + " (" + resourceCount + ")";
    }
}
