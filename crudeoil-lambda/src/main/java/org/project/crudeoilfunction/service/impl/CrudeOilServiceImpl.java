package org.project.crudeoilfunction.service.impl;


import com.amazonaws.services.lambda.runtime.Context;
import org.project.crudeoilfunction.config.CrudeOilConfiguration;
import org.project.crudeoilfunction.dao.CrudeOilDao;
import org.project.crudeoilfunction.domain.ApiRequest;
import org.project.crudeoilfunction.domain.ApiResponse;
import org.project.crudeoilfunction.service.CrudeOilService;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;


public class CrudeOilServiceImpl implements CrudeOilService {


    @Override
    public ApiResponse getCrudeOilData(ApiRequest request, Context context) {
        return CrudeOilConfiguration.getWebClient().get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("format", request.getFormat())
                        .queryParam("offset", request.getOffset())
                        .queryParam("limit", request.getLimit()).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .doOnSuccess(apiResponse -> new CrudeOilDao().saveCrudeOil(apiResponse, context))
                .block();
//        return null;
    }
}
