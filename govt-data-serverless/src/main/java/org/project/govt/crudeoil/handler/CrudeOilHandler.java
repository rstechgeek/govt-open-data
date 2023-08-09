package org.project.govt.crudeoil.handler;

import org.project.govt.crudeoil.domain.ApiRequest;
import org.project.govt.crudeoil.domain.ApiResponse;
import org.project.govt.crudeoil.service.CrudeOilService;
import org.project.govt.crudeoil.service.impl.CrudeOilServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class CrudeOilHandler implements Function<ApiRequest, Mono<ApiResponse>> {

    private  CrudeOilService crudeOilService;

    @Override
    public Mono<ApiResponse> apply(ApiRequest apiRequest) {

//        return Mono.just(ApiResponse.builder().status("Check").build());
        return crudeOilService.getCrudeOilData(apiRequest);
    }
}
