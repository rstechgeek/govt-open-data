package org.project.crudeoilfunction.service;


import org.project.crudeoilfunction.domain.ApiRequest;
import org.project.crudeoilfunction.domain.ApiResponse;
import reactor.core.publisher.Mono;

public interface CrudeOilService {
    Mono<ApiResponse> getCrudeOilData(ApiRequest request);
}
