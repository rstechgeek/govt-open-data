package org.project.resourceservice.mapper;

import org.project.resourceservice.dto.response.OrgResponse;
import org.project.resourceservice.dto.response.SectorResponse;
import org.project.resourceservice.entity.OrgView;
import org.project.resourceservice.entity.SectorView;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting view entities to response DTOs.
 */
@Component
public class ViewMapper {

    /**
     * Convert OrgView entity to OrgResponse DTO.
     */
    public OrgResponse toOrgResponse(OrgView orgView) {
        if (orgView == null) {
            return null;
        }
        // Calculate count from comma-separated index_ids
        int count = orgView.getIndexIds() != null && !orgView.getIndexIds().isEmpty()
                ? orgView.getIndexIds().split(",").length
                : 0;
        return new OrgResponse(
                orgView.getOrgName(),
                count);
    }

    /**
     * Convert SectorView entity to SectorResponse DTO.
     */
    public SectorResponse toSectorResponse(SectorView sectorView) {
        if (sectorView == null) {
            return null;
        }
        // Calculate count from comma-separated index_ids
        int count = sectorView.getIndexIds() != null && !sectorView.getIndexIds().isEmpty()
                ? sectorView.getIndexIds().split(",").length
                : 0;
        return new SectorResponse(
                sectorView.getSectorName(),
                count);
    }
}
