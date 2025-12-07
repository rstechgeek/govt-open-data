package org.project.resourceservice.service;

import reactor.core.publisher.Mono;

/**
 * Service for managing application metadata.
 * Handles operations related to app_metadata table.
 */
public interface MetadataService {

    /**
     * Get the last resource load time.
     * 
     * @return Last load time as string, or "Never" if not found
     */
    Mono<String> getLastLoadTime();

    /**
     * Update the last resource load time to current time.
     * 
     * @return Completion signal
     */
    Mono<Void> updateLastLoadTime();
}
