package org.example.godfx.model;

/**
 * Model representing resource statistics.
 */
public class ResourceStats {
    private Long count;
    private String lastLoadTime;

    public ResourceStats() {
    }

    public ResourceStats(Long count, String lastLoadTime) {
        this.count = count;
        this.lastLoadTime = lastLoadTime;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getLastLoadTime() {
        return lastLoadTime;
    }

    public void setLastLoadTime(String lastLoadTime) {
        this.lastLoadTime = lastLoadTime;
    }
}
