package org.project.resourceservice.repository;

import org.junit.jupiter.api.Test;
import org.project.resourceservice.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.util.UUID;

@DataR2dbcTest
@Testcontainers
public class ResourceRepositoryTest {

        @Container
        @ServiceConnection
        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
                        .withDatabaseName("govtdb")
                        .withUsername("ravi")
                        .withPassword("ravi");

        @Autowired
        private ResourceRepository resourceRepository;

        @Test
        void testSaveAndFindResource() {
                String indexId = UUID.randomUUID().toString();
                String records = "{\"key\": \"value\"}";
                Resource resource = Resource.builder()
                                .index_id(indexId)
                                .records(records)
                                .build();

                resourceRepository.save(resource)
                                .as(StepVerifier::create)
                                .expectNextMatches(saved -> saved.getIndex_id().equals(indexId)
                                                && saved.getRecords().equals(records))
                                .verifyComplete();

                resourceRepository.findById(indexId)
                                .as(StepVerifier::create)
                                .expectNextMatches(found -> found.getRecords().equals(records))
                                .verifyComplete();
        }
}
