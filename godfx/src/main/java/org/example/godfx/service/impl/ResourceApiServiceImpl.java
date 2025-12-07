package org.example.godfx.service.impl;

import javafx.application.Platform;
import org.example.godfx.config.ApiConfig;
import org.example.godfx.model.Organization;
import org.example.godfx.model.ResourceStats;
import org.example.godfx.model.Sector;
import org.example.godfx.service.ResourceApiService;
import org.example.godfx.util.HttpClientUtil;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Implementation of ResourceApiService.
 * Handles all HTTP communication with the backend.
 */
public class ResourceApiServiceImpl implements ResourceApiService {

    private final HttpClientUtil httpClient;

    public ResourceApiServiceImpl() {
        this.httpClient = new HttpClientUtil();
    }

    @Override
    public CompletableFuture<List<Organization>> fetchOrganizations() {
        return httpClient.getArray(ApiConfig.Endpoints.ORGANIZATIONS, Organization[].class)
                .thenApply(Arrays::asList);
    }

    @Override
    public CompletableFuture<List<Sector>> fetchSectors() {
        return httpClient.getArray(ApiConfig.Endpoints.SECTORS, Sector[].class)
                .thenApply(Arrays::asList);
    }

    @Override
    public CompletableFuture<ResourceStats> fetchStats() {
        CompletableFuture<Long> countFuture = httpClient.get(ApiConfig.Endpoints.COUNT, CountResponse.class)
                .thenApply(CountResponse::count);

        CompletableFuture<String> timeFuture = httpClient.get(ApiConfig.Endpoints.LAST_LOAD_TIME, TimeResponse.class)
                .thenApply(TimeResponse::lastLoadTime);

        return countFuture.thenCombine(timeFuture, ResourceStats::new);
    }

    @Override
    public CompletableFuture<Void> loadResources(Consumer<String> progressCallback) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(httpClient.getBaseUrl() + ApiConfig.Endpoints.LOAD))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return httpClient.getClient()
                .sendAsync(request, HttpResponse.BodyHandlers.ofLines())
                .thenAccept(response -> {
                    response.body().forEach(line -> {
                        // Update UI on JavaFX thread
                        Platform.runLater(() -> progressCallback.accept(line));
                    });
                });
    }

    @Override
    public CompletableFuture<Void> deleteAllResources() {
        return httpClient.delete(ApiConfig.Endpoints.DELETE_ALL);
    }

    // Helper records for JSON parsing
    private record CountResponse(Long count) {
    }

    private record TimeResponse(String lastLoadTime) {
    }
}
