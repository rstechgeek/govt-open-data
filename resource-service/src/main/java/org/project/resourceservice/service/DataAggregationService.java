package org.project.resourceservice.service;

import org.project.resourceservice.dto.response.OrgResponse;
import org.project.resourceservice.dto.response.SectorResponse;
import reactor.core.publisher.Flux;

/**
 * Service for data aggregation operations.
 * Handles queries against database views.
 */
public interface DataAggregationService {

    /**
     * Get all organizations with their resource counts.
     * 
     * @return Flux of organization responses
     */
    Flux<OrgResponse> getAllOrganizations();

    /**
     * Get all sectors with their resource counts.
     * 
     * @return Flux of sector responses
     */
    Flux<SectorResponse> getAllSectors();
}
