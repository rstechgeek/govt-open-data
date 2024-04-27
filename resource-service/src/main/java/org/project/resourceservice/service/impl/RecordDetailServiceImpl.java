package org.project.resourceservice.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.project.resourceservice.config.CommonProperties;
import org.project.resourceservice.entity.Resource;
import org.project.resourceservice.exception.ResourceException;
import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.model.RecordDetail;
import org.project.resourceservice.repository.ResourceRepository;
import org.project.resourceservice.service.RecordDetailService;
import org.project.resourceservice.util.ConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
public class RecordDetailServiceImpl implements RecordDetailService {
    private final WebClient webClient;

    public RecordDetailServiceImpl(WebClient webClient, ResourceRepository resourceRepository, CommonProperties properties) {
        this.webClient = webClient;
    }

    @Override
    public Flux<RecordDetail> getRecordDetails(ApiRequest request) {
//        if (Objects.isNull(request.getResourceId())) {
//            return Mono.empty();
//        }
//        Mono<RecordDetail> resourceOptional = this.resourceRepository.findById(request.getResourceId())
//                .flatMap(this::mapActiveRecordDetail)
//        if (resourceOptional.isPresent()) {
//            Resource resource = resourceOptional.get();
//            return this.webClient.get().uri(uriBuilder ->
//                            uriBuilder.path(Common.PATH_RESOURCE.concat("/").concat(resource.getIndex_id()))
//                                    .queryParam("api-key", commonProperties.getApiKey())
//                                    .queryParam("format", request.getFormat())
//                                    .queryParam("offset", request.getOffset())
//                                    .queryParam("limit", request.getLimit()).build())
//                    .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(RecordDetail.class);
//        }

        return Flux.empty();
    }

    private Mono<RecordDetail> mapActiveRecordDetail(Resource resource) {

        RecordDetail recordDetail = null;
        try {
            recordDetail = ConvertUtil.jsonToObject(resource.getRecord(), new TypeReference<>() {
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
