package org.project.resourceservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.resourceservice.annotation.ExecutionTime;
import org.project.resourceservice.common.Common;
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

    public ResourceServiceImpl(WebClient webClient, ResourceRepository resourceRepository, ObjectMapper mapper) {
        this.webClient = webClient;
        this.resourceRepository = resourceRepository;
        this.objectMapper = mapper;
    }

    @Override
    public Mono<ApiResponse> getResources(ApiRequest request) {
        log.info("Calling API ");
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder.path(Common.PATH_LISTS)
                        .queryParam("format", request.getFormat())
                        .queryParam("offset", request.getOffset())
                        .queryParam("limit", request.getLimit()).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ApiResponse.class);
    }

    @Override
    @ExecutionTime
    public void loadAllResource() {

        // make initial hit to get total and then parallely hit the request and store
        // the data into file
        ApiRequest request = ApiRequest.builder().format("json").limit(1000).offset(1).build();

        ApiResponse response = this.getResources(request).block();

        if (Objects.isNull(response)) {
            log.info("No data to load in DB");
            return;
        }
//        log.info(response.toString());
        saveRecords(Mono.just(response));

        float totalResource = response.getTotal();
        log.info("Total Resource available: " + totalResource);

        if (totalResource <= request.getLimit()) {
            log.info(response.getRecordDetails().size() + " no of data loaded in DB");
            return;
        }
        List<RecordDetail> recordDetails = new ArrayList<>();
        try {

            while (request.getOffset() < totalResource) {
                int newOffSet = request.getLimit() + request.getOffset();
                request.setOffset(newOffSet);
//
                // fetch all resources from API and store into postgres DB
                saveRecords(this.getResources(request));


//
//                saveRecords(this.getResources(request)
//                        .doOnError(throwable -> log.info("Error occured while fetching data " + throwable.getLocalizedMessage())));
//
//
            }

        } catch (Exception e) {
            log.warn(request.getOffset() + " no of data loaded with below exception.\n" + e.getLocalizedMessage());
        }


    }


    private void saveRecords(Mono<ApiResponse> responseMono) {
        log.info("Save method called");

        responseMono.flatMap(apiResponse -> Mono.just(apiResponse.getRecordDetails().stream().parallel()
                        .map(recordDetail -> Resource.builder()
                                .index_id(recordDetail.getIndexName())
                                .record(convertToJson(recordDetail))
                                .build()))).map(resourceStream -> resourceStream.collect(Collectors.toList()))
                .doOnSuccess(resourceRepository::saveAll)
                .doOnError(throwable -> log.info("Error occured while fetching data " + throwable.getLocalizedMessage()));


//        log.info("records saved " + disposed);
//        resource.ifPresent(resources ->  resourceRepository.save(resources.get(0)).subscribe(resource1 -> log.info("Saved resource "+resource1)));
//               .subscribe(recordDetails -> recordDetails.stream().parallel().map( recordDetail -> )) ;
//    forEach(recordDetail ->
//                resourceRepository.save(Resource.builder().index_id(recordDetail.getIndexName()).record(convertToJson(recordDetail)).build()));
    }

    private String convertToJson(RecordDetail recordDetail) {
        try {
            return objectMapper.writeValueAsString(recordDetail);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
