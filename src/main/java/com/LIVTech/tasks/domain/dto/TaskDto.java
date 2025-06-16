package com.LIVTech.tasks.domain.dto;

import com.LIVTech.tasks.domain.entities.TaskPriority;
import com.LIVTech.tasks.domain.entities.TaskStatus;

import java.time.LocalDateTime;

public record TaskDto(
    Long id,
    String title,
    String description,
    LocalDateTime dueDate,
    TaskPriority priority,
    TaskStatus status

) {
}
