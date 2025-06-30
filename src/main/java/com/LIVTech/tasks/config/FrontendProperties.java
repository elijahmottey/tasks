// src/main/java/com/LIVTech/tasks/config/FrontendProperties.java
package com.LIVTech.tasks.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotBlank;

/**
 * Binds to the "frontend" prefix in the application.yml file.
 * This provides type-safe access to frontend-related configuration.
 */
@Validated
@ConfigurationProperties(prefix = "frontend")
public record FrontendProperties(
        @NotBlank String url
) {
}