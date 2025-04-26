package org.project.resourceservice.service;

import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.model.RecordDetail;
import reactor.core.publisher.Flux;

public interface RecordDetailService {

    Flux<RecordDetail> getRecordDetailsFromOGDServer(ApiRequest request);

//    Flux<Resource> getActiveResource();
}
