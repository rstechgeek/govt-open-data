package org.project.resourceservice.service.impl;

import org.project.resourceservice.annotation.ExecutionTime;
import org.project.resourceservice.common.Common;

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
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ResourceServiceImpl implements ResourceService {
        private static final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);
        private final WebClient webClient;
        private final ResourceRepository resourceRepository;

        public ResourceServiceImpl(WebClient webClient, ResourceRepository resourceRepository) {
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
                                .exchangeToMono(clientResponse -> clientResponse.statusCode().is2xxSuccessful()
                                                ? clientResponse.bodyToMono(ApiResponse.class)
                                                                .onErrorResume(throwable -> {
                                                                        log.info("Exception while converting object to json {}",
                                                                                        throwable.getMessage());
                                                                        return Mono.just(ApiResponse.builder().build());
                                                                })
                                                : Mono.error(() -> new Exception("Error while calling API "
                                                                + clientResponse.statusCode())));
        }

        @ExecutionTime
        @Override
        public Flux<String> loadAllResource() {

                // make initial hit to get total and then parallely hit the request and store
                // the data into file
                ApiRequest request = ApiRequest.builder().format("json").limit(1).offset(1).build();
                // first get total no of records available
                // AtomicInteger total = new AtomicInteger();
                int limit = 1000;
                int concurrencyLevel = 5; // Tune as needed
                int maxRetries = 3;
                Duration retryBackoff = Duration.ofSeconds(2);

                return this.getResources(request)
                                .flatMapMany(apiResponse -> {
                                        float noOfResource = apiResponse.getTotal();
                                        int total = (noOfResource >= Integer.MIN_VALUE
                                                        && noOfResource <= Integer.MAX_VALUE)
                                                                        ? Math.round(noOfResource)
                                                                        : 0;

                                        if (total == 0) {
                                                log.warn("Total number of resources is zero or invalid.");
                                                return Flux.just("Total number of resources is zero or invalid.");
                                        }

                                        log.info("Total {} resources", total);
                                        int totalPages = (int) Math.ceil((double) total / limit);

                                        return Flux.range(0, totalPages)
                                                        .map(pageNumber -> ApiRequest.builder()
                                                                        .format("json")
                                                                        .limit(limit)
                                                                        .offset(limit * pageNumber)
                                                                        .build());
                                })
                                .flatMap(objRequest -> this.getResources((ApiRequest) objRequest)
                                                .retryWhen(Retry.backoff(maxRetries, retryBackoff)
                                                                .onRetryExhaustedThrow(
                                                                                (spec, signal) -> signal.failure()))
                                                .doOnError(e -> log.error("Failed to fetch data for request: {}",
                                                                objRequest, e)),
                                                concurrencyLevel // limit parallel requests
                                )
                                .flatMap(this::saveResources)
                                .scan(0, Integer::sum)
                                .sample(Duration.ofMillis(100))
                                .map(count -> "Saved records: " + count)
                                .doOnError(e -> log.error("Unexpected error during resource loading", e))
                                .onErrorContinue((throwable, obj) -> log.warn("Continuing despite error on: {}", obj));
        }

        public Flux<Integer> saveResources(ApiResponse apiResponse) {

                if (Objects.nonNull(apiResponse.getRecordDetails())) {
                        return Flux.fromIterable(apiResponse.getRecordDetails()).ofType(RecordDetail.class)
                                        .map(recordDetail -> {
                                                String jsonString = "";
                                                try {
                                                        jsonString = ConvertUtil.objectToJson(recordDetail);
                                                } catch (ResourceException e) {
                                                        return Resource.builder().build(); // Should handle better, but
                                                                                           // kept simple
                                                }
                                                return Resource.builder()
                                                                .index_id(recordDetail.getIndexName())
                                                                .records(io.r2dbc.postgresql.codec.Json.of(jsonString))
                                                                .build();
                                        })
                                        .filter(resource -> resource.getIndex_id() != null)
                                        .flatMap(resource -> resourceRepository
                                                        .existsById(Objects.requireNonNull(resource.getId()))
                                                        .flatMap(exists -> exists ? Mono.empty() : Mono.just(resource)))
                                        .flatMap(resourceRepository::save)
                                        .map(resource -> 1)
                                        .doOnError(throwable -> log.error("Data not saved due to {}",
                                                        throwable.getMessage()));
                }
                return Flux.empty();
        }

}
