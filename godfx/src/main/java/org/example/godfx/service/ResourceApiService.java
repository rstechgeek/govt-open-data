package org.example.godfx.service;

import org.example.godfx.model.Organization;
import org.example.godfx.model.ResourceStats;
import org.example.godfx.model.Sector;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Service for resource-related API operations.
 * Abstracts HTTP communication from controllers.
 */
public interface ResourceApiService {

    /**
     * Fetch all organizations from the backend.
     * 
     * @return Future containing list of organizations
     */
    CompletableFuture<List<Organization>> fetchOrganizations();

    /**
     * Fetch all sectors from the backend.
     * 
     * @return Future containing list of sectors
     */
    CompletableFuture<List<Sector>> fetchSectors();

    /**
     * Fetch resource statistics (count and last load time).
     * 
     * @return Future containing resource stats
     */
    CompletableFuture<ResourceStats> fetchStats();

    /**
     * Trigger resource loading operation.
     * 
     * @param progressCallback Callback for progress updates
     * @return Future that completes when loading is done
     */
    CompletableFuture<Void> loadResources(Consumer<String> progressCallback);

    /**
     * Delete all resources from the backend.
     * 
     * @return Future that completes when deletion is done
     */
    CompletableFuture<Void> deleteAllResources();
}
