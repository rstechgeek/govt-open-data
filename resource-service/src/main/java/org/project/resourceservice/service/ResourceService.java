package org.project.resourceservice.service;

import org.project.resourceservice.entity.Resource;
import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.model.ApiResponse;
import org.project.resourceservice.model.RecordDetail;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ResourceService {
    public Mono<ApiResponse> getResources(ApiRequest request);

    public void loadAllResource();

    public Mono<RecordDetail> getResource(ApiRequest request);

    public Flux<Resource> getActiveResource();
}
