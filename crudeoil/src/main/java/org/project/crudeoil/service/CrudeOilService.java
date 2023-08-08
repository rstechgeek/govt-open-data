package org.project.crudeoil.service;

import org.project.crudeoil.domain.ApiRequest;
import org.project.crudeoil.domain.ApiResponse;
import reactor.core.publisher.Mono;

public interface CrudeOilService {
    Mono<ApiResponse> getCrudeOilData(ApiRequest request);
}
