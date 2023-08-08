package org.project.crudeoil.service.impl;

import org.project.crudeoil.domain.ApiRequest;
import org.project.crudeoil.domain.ApiResponse;
import org.project.crudeoil.service.CrudeOilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CrudeOilServiceImpl implements CrudeOilService {


    private final WebClient webClient;

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