package org.project.govtdata.crudeoil.service.impl;

import org.project.govtdata.crudeoil.domain.ApiRequest;
import org.project.govtdata.crudeoil.domain.ApiResponse;
import org.project.govtdata.crudeoil.service.CrudeOilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CrudeOilServiceImpl implements CrudeOilService {


    private WebClient webClient;

    @Autowired
    public CrudeOilServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Mono<ApiResponse> getCrudeOilData(ApiRequest request) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("format", request.getFormat())
                        .queryParam("offset", request.getOffset())
                        .queryParam("limit", request.getLimit()).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ApiResponse.class);
    }
}
