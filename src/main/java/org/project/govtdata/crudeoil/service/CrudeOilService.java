package org.project.govtdata.crudeoil.service;

import org.project.govtdata.crudeoil.domain.ApiRequest;
import org.project.govtdata.crudeoil.domain.ApiResponse;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

public interface CrudeOilService {
    Mono<ApiResponse> getCrudeOilData(ApiRequest request);
}
