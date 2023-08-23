package org.project.resourceservice.repository;

import org.project.resourceservice.entity.Resource;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends ReactiveCrudRepository<Resource, String> {
}
