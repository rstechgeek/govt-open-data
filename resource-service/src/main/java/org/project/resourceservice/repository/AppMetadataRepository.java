package org.project.resourceservice.repository;

import org.project.resourceservice.entity.AppMetadata;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppMetadataRepository extends ReactiveCrudRepository<AppMetadata, String> {
}
