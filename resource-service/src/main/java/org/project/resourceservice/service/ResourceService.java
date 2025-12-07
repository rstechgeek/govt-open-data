package org.project.resourceservice.service;

import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.model.ApiResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service for resource management operations.
 * Focused on core resource CRUD and loading operations.
 */
public interface ResourceService {

     Mono<ApiResponse> getResources(ApiRequest request);

     Flux<String> loadAllResource();

     Mono<Long> getCount();

     Mono<Void> deleteAll();
}
