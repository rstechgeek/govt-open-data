package org.project.resourceservice.service;

import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.model.ApiResponse;
import reactor.core.publisher.Mono;

public interface ResourceService {
    public Mono<ApiResponse> getResources(ApiRequest request);
    public void loadAllResource();
}
