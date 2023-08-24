package org.project.resourceservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.resourceservice.annotation.ExecutionTime;
import org.project.resourceservice.common.Common;
import org.project.resourceservice.config.CommonProperties;
import org.project.resourceservice.repository.ResourceRepository;
import org.project.resourceservice.entity.Resource;
import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.model.ApiResponse;
import org.project.resourceservice.model.RecordDetail;
import org.project.resourceservice.service.ResourceService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ResourceServiceImpl implements ResourceService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final ResourceRepository resourceRepository;
    private final CommonProperties commonProperties;

    public ResourceServiceImpl(WebClient webClient, ResourceRepository resourceRepository, ObjectMapper mapper, CommonProperties properties) {
        this.webClient = webClient;
        this.resourceRepository = resourceRepository;
        this.objectMapper = mapper;
        this.commonProperties = properties;
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
                                ? clientResponse.bodyToMono(ApiResponse.class)
                                : Mono.error(() -> new Exception("Error while calling API " + clientResponse.statusCode())));
    }

    @Override
    @ExecutionTime
    public void loadAllResource() {

        // make initial hit to get total and then parallely hit the request and store
        // the data into file
        ApiRequest request = ApiRequest.builder().format("json").limit(100).offset(1).build();

        ApiResponse response = this.getResources(request).block();

        if (Objects.isNull(response)) {
            log.info("No data to load in DB");
            return;
        }

        saveRecords(Mono.just(response));

        float totalResource = response.getTotal();
        log.info("Total Resource available: " + totalResource);

        if (totalResource <= request.getLimit()) {
            log.info(response.getRecordDetails().size() + " no of data loaded in DB");
            return;
        }
        try {

            while (request.getOffset() < totalResource) {
                int newOffSet = request.getLimit() + request.getOffset();
                request.setOffset(newOffSet);

                // fetch all resources from API and store into postgres DB
                saveRecords(this.getResources(request));

            }

        } catch (Exception e) {
            log.warn(request.getOffset() + " no of data loaded with below exception.\n" + e.getLocalizedMessage());
        }


    }

    @Override
    public Mono<RecordDetail> getResource(ApiRequest request) {
        if (Objects.isNull(request.getResourceId())) {
            return Mono.empty();
        }
        Optional<Resource> resourceOptional = this.resourceRepository.findById(request.getResourceId())
                .filter(this::isResourceActive).blockOptional();
        if (resourceOptional.isPresent()) {
            Resource resource = resourceOptional.get();
            return this.webClient.get().uri(uriBuilder ->
                            uriBuilder.path(Common.PATH_RESOURCE.concat("/").concat(resource.getIndex_id()))
                                    .queryParam("api-key", commonProperties.getApiKey())
                                    .queryParam("format", request.getFormat())
                                    .queryParam("offset", request.getOffset())
                                    .queryParam("limit", request.getLimit()).build())
                    .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(RecordDetail.class);
        }

        return Mono.empty();
    }


    public Flux<Resource> getActiveResource() {
        return this.resourceRepository.findAll().filter(resource -> {
            RecordDetail recordDetail = null;
            try {
                recordDetail = objectMapper.readValue(resource.getRecord(), RecordDetail.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            if (Objects.nonNull(recordDetail)) {
                return recordDetail.getActive().equals("1");
            }
            return false;
        });
    }

    private Boolean isResourceActive(Resource resource) {
        try {
            RecordDetail recordDetail = objectMapper.readValue(resource.getRecord(), RecordDetail.class);
            if (Objects.nonNull(recordDetail)) {
                return recordDetail.getActive().equals("1");
            } else {
                log.info("Provided resource id (" + resource.getIndex_id() + ") is not active.");
            }
        } catch (JsonProcessingException e) {
            log.warn(e.getLocalizedMessage());
        }
        return false;
    }


    private void saveRecords(Mono<ApiResponse> responseMono) {
        log.info("Save method called");

        responseMono.flatMapMany(apiResponse -> Flux.fromIterable(apiResponse.getRecordDetails().stream().parallel()
                        .map(recordDetail -> Resource.builder()
                                .index_id(recordDetail.getIndexName())
                                .record(convertToJson(recordDetail))
                                .build()).collect(Collectors.toList())))
                .filter(resource -> resource.getId() != null)
                .filter(resource -> resourceRepository.findById(resource.getId()).equals(Mono.just(resource)))
                .flatMap(resourceRepository::save)
                .doOnError(throwable -> log.info("Error while saving records " + throwable.getLocalizedMessage()))
                .log(log.getName(), Level.INFO)
                .subscribe(resource -> log.info("From Subscriber " + resource.getIndex_id()));
    }

    private String convertToJson(RecordDetail recordDetail) {
        try {
            return objectMapper.writeValueAsString(recordDetail);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
