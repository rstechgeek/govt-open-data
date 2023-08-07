package org.project.govtdata.crudeoil.controller;

import lombok.extern.slf4j.Slf4j;
import org.project.govtdata.crudeoil.domain.ApiRequest;
import org.project.govtdata.crudeoil.domain.ApiResponse;
import org.project.govtdata.crudeoil.service.CrudeOilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class CrudeOilController {

    private CrudeOilService crudeOilService;

    @Autowired
    public CrudeOilController(CrudeOilService crudeOilService) {
        this.crudeOilService = crudeOilService;
    }

    @GetMapping(value = "/crude-oil")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<ApiResponse>> getCrudeOil(@RequestBody ApiRequest request) {
        log.info(request.toString());
        return crudeOilService.getCrudeOilData(request).map(response -> ResponseEntity.ok().body(response));
    }

}
