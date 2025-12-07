package org.project.resourceservice.controller;

import org.project.resourceservice.dto.response.*;
import org.project.resourceservice.service.DataAggregationService;
import org.project.resourceservice.service.MetadataService;
import org.project.resourceservice.service.ResourceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * REST Controller for resource management operations.
 * Uses specialized services for different concerns.
 */
@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;
    private final DataAggregationService dataAggregationService;
    private final MetadataService metadataService;

    public ResourceController(
            ResourceService resourceService,
            DataAggregationService dataAggregationService,
            MetadataService metadataService) {
        this.resourceService = resourceService;
        this.dataAggregationService = dataAggregationService;
        this.metadataService = metadataService;
    }

    @GetMapping("/orgs")
    public Flux<OrgResponse> getAllOrgs() {
        return dataAggregationService.getAllOrganizations();
    }

    @GetMapping("/sectors")
    public Flux<SectorResponse> getAllSectors() {
        return dataAggregationService.getAllSectors();
    }

    @PostMapping(value = "/load", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> loadResources() {
        return resourceService.loadAllResource()
                .doOnComplete(() -> metadataService.updateLastLoadTime().subscribe());
    }

    @GetMapping("/count")
    public Mono<ResourceCountResponse> getCount() {
        return resourceService.getCount()
                .map(ResourceCountResponse::new);
    }

    @DeleteMapping("/all")
    public Mono<Void> deleteAll() {
        return resourceService.deleteAll();
    }

    @GetMapping("/last-load-time")
    public Mono<LastLoadTimeResponse> getLastLoadTime() {
        return metadataService.getLastLoadTime()
                .map(LastLoadTimeResponse::new);
    }
}
