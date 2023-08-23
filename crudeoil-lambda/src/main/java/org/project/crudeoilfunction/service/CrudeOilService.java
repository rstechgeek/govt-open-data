package org.project.crudeoilfunction.service;


import com.amazonaws.services.lambda.runtime.Context;
import org.project.crudeoilfunction.domain.ApiRequest;
import org.project.crudeoilfunction.domain.ApiResponse;
import reactor.core.publisher.Mono;

public interface CrudeOilService {
    ApiResponse getCrudeOilData(ApiRequest request, Context context);
}
