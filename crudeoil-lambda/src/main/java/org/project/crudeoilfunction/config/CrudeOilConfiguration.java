package org.project.crudeoilfunction.config;

import org.springframework.web.reactive.function.client.WebClient;


public class CrudeOilConfiguration {

    private static final String BASE_URL = "https://api.data.gov.in/resource";
    private static final String API_KEY = "579b464db66ec23bdd000001f37cd922c0e24d677a89c19d71e98829";
    private static final String RESOURCE = "8d3b6596-b09e-4077-aebf-425193185a5b";


    public static WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(BASE_URL.concat("/").concat(RESOURCE).concat("?api-key=").concat(API_KEY)).build();
    }

}
