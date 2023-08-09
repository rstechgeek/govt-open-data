package org.project.govt.crudeoil.service;

import org.project.govt.crudeoil.domain.ApiRequest;
import org.project.govt.crudeoil.domain.ApiResponse;
import reactor.core.publisher.Mono;

public interface CrudeOilService {
    Mono<ApiResponse> getCrudeOilData(ApiRequest request);
}
