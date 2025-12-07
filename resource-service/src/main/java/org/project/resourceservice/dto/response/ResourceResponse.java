package org.project.resourceservice.dto.response;

import java.time.LocalDateTime;

/**
 * Response DTO for resource information.
 * Separates API contract from domain entity.
 */
public record ResourceResponse(
        String indexId,
        String title,
        String organization,
        String sector,
        LocalDateTime createdAt) {
}
