package org.project.resourceservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.resourceservice.entity.Resource;
import org.project.resourceservice.model.ApiRequest;
import org.project.resourceservice.model.ApiResponse;
import org.project.resourceservice.repository.ResourceRepository;
import org.project.resourceservice.service.impl.ResourceServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;

public class ResourceServiceExternalTest {

    private MockWebServer mockWebServer;
    private ResourceServiceImpl resourceService;
    private ResourceRepository resourceRepository;

    @BeforeEach
    void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        WebClient webClient = WebClient.builder()
                .baseUrl(mockWebServer.url("/").toString())
                .build();

        resourceRepository = Mockito.mock(ResourceRepository.class);
        resourceService = new ResourceServiceImpl(webClient, resourceRepository);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void testGetResourcesSuccess() throws Exception {
        ApiResponse mockResponse = ApiResponse.builder()
                .status("ok")
                .total(10)
                .count(1)
                .build();

        // Manual JSON serialization since we don't have ObjectMapper configured here
        // or we can use rudimentary string json
        String jsonResponse = "{\"status\":\"ok\",\"total\":10,\"count\":1}";

        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonResponse)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));

        ApiRequest request = ApiRequest.builder()
                .format("json")
                .offset(0)
                .limit(10)
                .build();

        StepVerifier.create(resourceService.getResources(request))
                .expectNextMatches(response -> "ok".equals(response.getStatus()) && response.getTotal() == 10)
                .verifyComplete();
    }
}
