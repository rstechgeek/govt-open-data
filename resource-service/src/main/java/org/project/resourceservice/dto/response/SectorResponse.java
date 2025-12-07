package org.project.resourceservice.dto.response;

/**
 * Response DTO for sector information.
 */
public record SectorResponse(
        String sectorName,
        Integer resourceCount) {
}
