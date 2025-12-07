package org.project.resourceservice.controller;

import org.project.resourceservice.service.ResourceService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping(value = "/load", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> loadResources() {
        return resourceService.loadAllResource();
    }
}
