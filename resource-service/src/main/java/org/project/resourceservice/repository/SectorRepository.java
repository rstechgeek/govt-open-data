package org.project.resourceservice.repository;

import org.project.resourceservice.entity.SectorView;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends ReactiveCrudRepository<SectorView, String> {
}
