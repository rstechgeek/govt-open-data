package org.project.resourceservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;

import org.project.resourceservice.common.Common;

import org.project.resourceservice.entity.Resource;
import org.project.resourceservice.exception.ResourceException;
import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.model.RecordDetail;
import org.project.resourceservice.service.RecordDetailService;
import org.project.resourceservice.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class RecordDetailServiceImpl implements RecordDetailService {
    @Value("${open.data.gov.apikey}")
    private String apiKey;
    private final WebClient webClient;

    public RecordDetailServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public Flux<RecordDetail> getRecordDetails(ApiRequest request) {
        if (Objects.isNull(request.getResourceId())) {
            return Flux.error(new ResourceException("", "Resource id cannot be empty."));
        }
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path(Common.PATH_RESOURCE.concat("/").concat(request.getResourceId()))
                        .queryParam("api-key", apiKey)
                        .queryParam("format", request.getFormat())
                        .queryParam("offset", request.getOffset())
                        .queryParam("limit", request.getLimit()).build())
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToFlux(clientResponse -> clientResponse.statusCode().is2xxSuccessful()
                        ? clientResponse.bodyToFlux(RecordDetail.class)
                        : Flux.error(
                                new ResourceException("", "Error while calling API " + clientResponse.statusCode())));
    }

    private Mono<RecordDetail> mapActiveRecordDetail(Resource resource) {

        RecordDetail recordDetail = null;
        try {
            recordDetail = ConvertUtil.jsonToObject(resource.getRecords().asString(), new TypeReference<>() {
            });
        } catch (ResourceException e) {
            return Mono.error(new ResourceException("", e.getMessage()));
        }

        if (Objects.nonNull(recordDetail)) {
            if (recordDetail.getActive().equalsIgnoreCase("1")) {
                return Mono.just(recordDetail);
            }
        } else {
            return Mono.error(new ResourceException("", "No record found"));
        }

        return Mono.empty();
    }
}
