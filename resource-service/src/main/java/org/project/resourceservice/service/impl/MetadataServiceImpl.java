package org.project.resourceservice.service.impl;

import org.project.resourceservice.entity.AppMetadata;
import org.project.resourceservice.repository.AppMetadataRepository;
import org.project.resourceservice.service.MetadataService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Implementation of MetadataService.
 * Manages application metadata operations.
 */
@Service
public class MetadataServiceImpl implements MetadataService {

    private static final String LAST_LOAD_TIME_KEY = "last_load_time";

    private final AppMetadataRepository appMetadataRepository;

    public MetadataServiceImpl(AppMetadataRepository appMetadataRepository) {
        this.appMetadataRepository = appMetadataRepository;
    }

    @Override
    public Mono<String> getLastLoadTime() {
        return appMetadataRepository.findById(LAST_LOAD_TIME_KEY)
                .map(AppMetadata::getValue)
                .switchIfEmpty(Mono.just("Never"));
    }

    @Override
    public Mono<Void> updateLastLoadTime() {
        AppMetadata metadata = new AppMetadata(
                LAST_LOAD_TIME_KEY,
                LocalDateTime.now().toString());
        return appMetadataRepository.save(metadata).then();
    }
}
