package org.project.resourceservice.repository;

import org.project.resourceservice.entity.OrgView;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgRepository extends ReactiveCrudRepository<OrgView, String> {
}
