package org.example.godfx.config;

/**
 * Configuration constants for the application.
 */
public class ApiConfig {

    /**
     * Base URL for the resource service API.
     */
    public static final String BASE_URL = "http://localhost:8091";

    /**
     * API endpoints.
     */
    public static class Endpoints {
        public static final String ORGANIZATIONS = "/resources/orgs";
        public static final String SECTORS = "/resources/sectors";
        public static final String COUNT = "/resources/count";
        public static final String LAST_LOAD_TIME = "/resources/last-load-time";
        public static final String LOAD = "/resources/load";
        public static final String DELETE_ALL = "/resources/all";
    }

    private ApiConfig() {
        // Utility class
    }
}
