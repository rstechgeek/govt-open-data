package org.project.resourceservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("view_sectors")
public class SectorView {
    @Id
    @Column("sector_name")
    private String sectorName;

    @Column("index_ids")
    private String indexIds;

    public SectorView() {
    }

    public SectorView(String sectorName, String indexIds) {
        this.sectorName = sectorName;
        this.indexIds = indexIds;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getIndexIds() {
        return indexIds;
    }

    public void setIndexIds(String indexIds) {
        this.indexIds = indexIds;
    }
}
