package org.project.resourceservice.dto.response;

/**
 * Response DTO for organization information.
 */
public record OrgResponse(
        String orgName,
        Integer resourceCount) {
}
