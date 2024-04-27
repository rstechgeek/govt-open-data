package org.project.resourceservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.project.resourceservice.annotation.ExecutionTime;
import org.project.resourceservice.common.Common;
import org.project.resourceservice.config.CommonProperties;
import org.project.resourceservice.entity.Resource;
import org.project.resourceservice.exception.ResourceException;
import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.model.ApiResponse;
import org.project.resourceservice.model.RecordDetail;
import org.project.resourceservice.repository.ResourceRepository;
import org.project.resourceservice.service.ResourceService;
import org.project.resourceservice.util.ConvertUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {
    private final WebClient webClient;
    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(WebClient webClient, ResourceRepository resourceRepository, ObjectMapper mapper, CommonProperties properties) {
        this.webClient = webClient;
        this.resourceRepository = resourceRepository;
    }

    @Override
    public Mono<ApiResponse> getResources(ApiRequest request) {
        log.info("Call getResources method");
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path(Common.PATH_LISTS)
                        .queryParam("format", request.getFormat())
                        .queryParam("offset", request.getOffset())
                        .queryParam("limit", request.getLimit()).build())
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse ->
                        clientResponse.statusCode().is2xxSuccessful()
                                ? clientResponse.bodyToMono(ApiResponse.class).onErrorResume(throwable -> {
                            log.info("Exception while converting object to json {}", throwable.getMessage());
                            return Mono.just(ApiResponse.builder().build());
                        })
                                : Mono.error(() -> new Exception("Error while calling API " + clientResponse.statusCode())));
    }

    @ExecutionTime
    @Override
    public void loadAllResource() {

        // make initial hit to get total and then parallely hit the request and store
        // the data into file
        ApiRequest request = ApiRequest.builder().format("json").limit(1).offset(1).build();
        //first get total no of records available
//        AtomicInteger total = new AtomicInteger();
        int limit = 1000;
        this.getResources(request).flatMap(apiResponse -> {
                    int total = 0;
                    float noOfResource = apiResponse.getTotal();
                    if (noOfResource >= Integer.MIN_VALUE && noOfResource <= Integer.MAX_VALUE) {
                        total = Math.round(noOfResource);
                        log.info("Total {} resources", total);
                    } else {
                        log.error("Total no of resource float value is out of range to int value.");
                    }
                    return Mono.just(total);
                })
                .flatMapMany(noOfRecords -> Flux.range(0, noOfRecords / limit))
                .map(pageNumber -> ApiRequest.builder().format("json").limit(limit).offset(limit * (pageNumber)).build()).log()
                .flatMap(this::getResources)
//                .delayElements(Duration.ofMillis(500))
                .subscribe(this::saveResources);

    }


    private void saveResources(ApiResponse apiResponse) {

        if (Objects.nonNull(apiResponse.getRecordDetails()))
            Flux.fromIterable(apiResponse.getRecordDetails()).ofType(RecordDetail.class).map(recordDetail ->
                    {
                        String jsonString = "";
                        try {
                            jsonString = ConvertUtil.objectToJson(recordDetail);
                        } catch (ResourceException e) {
                            Flux.error(new ResourceException("", e.getMessage()));
                        }
                        return Resource.builder()
                                .index_id(recordDetail.getIndexName())
                                .record(jsonString)
                                .build();
                    })
                    .flatMap(resource -> resourceRepository.existsById(Objects.requireNonNull(resource.getId()))
                            .flatMap(exists -> exists ? Mono.empty() : Mono.just(resource)))
                    .flatMap(resourceRepository::save)
                    .doOnNext(resource -> log.info("Data saved {}", resource.getIndex_id()))
                    .doOnError(throwable -> log.error("Data not saved due to {}", throwable.getMessage()))
//                .doOnNext(resource -> result.put(resource.getId(), "Saved"))
//                .doOnError(throwable -> result.put(resource.getId(), "Failed"))
                    .subscribe();
    }


}
