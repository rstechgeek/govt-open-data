package org.example.godfx.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.godfx.config.ApiConfig;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * Utility class for HTTP operations.
 * Centralizes HTTP client logic and JSON parsing.
 */
public class HttpClientUtil {

    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public HttpClientUtil() {
        this.client = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.baseUrl = ApiConfig.BASE_URL;
    }

    /**
     * Perform GET request and parse response to specified type.
     */
    public <T> CompletableFuture<T> get(String endpoint, Class<T> responseType) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + endpoint))
                .GET()
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(body -> {
                    try {
                        return objectMapper.readValue(body, responseType);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse response: " + e.getMessage(), e);
                    }
                });
    }

    /**
     * Perform GET request and parse response to array of specified type.
     */
    public <T> CompletableFuture<T[]> getArray(String endpoint, Class<T[]> arrayType) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + endpoint))
                .GET()
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(body -> {
                    try {
                        return objectMapper.readValue(body, arrayType);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to parse response: " + e.getMessage(), e);
                    }
                });
    }

    /**
     * Perform POST request.
     */
    public CompletableFuture<String> post(String endpoint) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + endpoint))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }

    /**
     * Perform DELETE request.
     */
    public CompletableFuture<Void> delete(String endpoint) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + endpoint))
                .DELETE()
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> null);
    }

    /**
     * Get the underlying HttpClient for advanced operations.
     */
    public HttpClient getClient() {
        return client;
    }

    /**
     * Get the base URL.
     */
    public String getBaseUrl() {
        return baseUrl;
    }
}
