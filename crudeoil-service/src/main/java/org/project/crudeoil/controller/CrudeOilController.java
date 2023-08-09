package org.project.crudeoil.controller;

import lombok.extern.slf4j.Slf4j;
import org.project.crudeoil.domain.ApiRequest;
import org.project.crudeoil.domain.ApiResponse;
import org.project.crudeoil.service.CrudeOilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api")
public class CrudeOilController {

    private final CrudeOilService crudeOilService;

    @Autowired
    public CrudeOilController(CrudeOilService crudeOilService) {
        this.crudeOilService = crudeOilService;
    }

    @GetMapping(value = "/v1/crude-oil")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<ApiResponse>> getCrudeOil(@RequestBody ApiRequest request) {
        return crudeOilService.getCrudeOilData(request)
                .map(apiResponse -> ResponseEntity.ok().body(apiResponse))
                .onErrorResume(throwable -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.builder().status(throwable.getMessage()).build())));
    }

}
