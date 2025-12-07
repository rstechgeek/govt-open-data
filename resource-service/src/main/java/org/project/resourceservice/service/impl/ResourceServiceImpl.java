package org.project.resourceservice.service.impl;

import org.project.resourceservice.entity.Resource;
import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.model.ApiResponse;
import org.project.resourceservice.repository.ResourceRepository;
import org.project.resourceservice.service.ResourceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import io.r2dbc.postgresql.codec.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ResourceServiceImpl implements ResourceService {
        private static final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);

        private final ResourceRepository resourceRepository;
        private final WebClient webClient;
        private final ObjectMapper objectMapper = new ObjectMapper();

        public ResourceServiceImpl(ResourceRepository resourceRepository,
                        WebClient.Builder webClientBuilder) {
                this.resourceRepository = resourceRepository;
                this.webClient = webClientBuilder.baseUrl("https://api.data.gov.in").build();
        }

        @Override
        public Mono<ApiResponse> getResources(ApiRequest request) {
                return webClient.get()
                                .uri(uriBuilder -> uriBuilder
                                                .path("/resource/9ef84268-d588-465a-a308-a864a43d0070")
                                                .queryParam("api-key",
                                                                "579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b")
                                                .queryParam("format", "json")
                                                .queryParam("limit", request.getLimit())
                                                .queryParam("offset", request.getOffset())
                                                .build())
                                .retrieve()
                                .bodyToMono(ApiResponse.class);
        }

        public Flux<Integer> saveResources(ApiResponse apiResponse) {
                if (apiResponse.getRecordDetails() == null) {
                        return Flux.empty();
                }
                return Flux.fromIterable(apiResponse.getRecordDetails())
                                .map(recordDetail -> {
                                        try {
                                                Json json = Json.of(objectMapper.writeValueAsString(recordDetail));
                                                return new Resource(recordDetail.getIndexName(), json);
                                        } catch (JsonProcessingException e) {
                                                throw new RuntimeException(e);
                                        }
                                })
                                .flatMap(resourceRepository::save)
                                .map(resource -> 1)
                                .onErrorResume(e -> {
                                        log.error("Error saving resource: " + e.getMessage());
                                        return Flux.empty();
                                });
        }

        @Override
        public Flux<String> loadAllResource() {
                return Flux.range(0, 100)
                                .flatMap(page -> {
                                        int limit = 10000;
                                        int offset = page * limit;
                                        ApiRequest request = ApiRequest.builder()
                                                        .limit(limit)
                                                        .offset(offset)
                                                        .format("json")
                                                        .build();
                                        return getResources(request)
                                                        .flatMapMany(this::saveResources);
                                })
                                .scan(0, Integer::sum)
                                .sample(Duration.ofMillis(100))
                                .map(count -> "Saved records: " + count);
                // Note: Metadata update moved to controller layer for better separation
        }

        @Override
        public Mono<Long> getCount() {
                return resourceRepository.count();
        }

        @Override
        public Mono<Void> deleteAll() {
                return resourceRepository.deleteAll();
        }
}
