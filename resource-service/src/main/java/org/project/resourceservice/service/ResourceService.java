package org.project.resourceservice.service;

import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.model.ApiResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ResourceService {
     Mono<ApiResponse> getResources(ApiRequest request);

     Flux<String> loadAllResource();

}
