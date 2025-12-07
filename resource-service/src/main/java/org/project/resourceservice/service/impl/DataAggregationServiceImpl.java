package org.project.resourceservice.service.impl;

import org.project.resourceservice.dto.response.OrgResponse;
import org.project.resourceservice.dto.response.SectorResponse;
import org.project.resourceservice.mapper.ViewMapper;
import org.project.resourceservice.repository.OrgRepository;
import org.project.resourceservice.repository.SectorRepository;
import org.project.resourceservice.service.DataAggregationService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * Implementation of DataAggregationService.
 * Handles aggregated data queries.
 */
@Service
public class DataAggregationServiceImpl implements DataAggregationService {

    private final OrgRepository orgRepository;
    private final SectorRepository sectorRepository;
    private final ViewMapper viewMapper;

    public DataAggregationServiceImpl(
            OrgRepository orgRepository,
            SectorRepository sectorRepository,
            ViewMapper viewMapper) {
        this.orgRepository = orgRepository;
        this.sectorRepository = sectorRepository;
        this.viewMapper = viewMapper;
    }

    @Override
    public Flux<OrgResponse> getAllOrganizations() {
        return orgRepository.findAll()
                .map(viewMapper::toOrgResponse);
    }

    @Override
    public Flux<SectorResponse> getAllSectors() {
        return sectorRepository.findAll()
                .map(viewMapper::toSectorResponse);
    }
}
