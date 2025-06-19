package com.LIVTech.tasks.domain.dto;

import com.LIVTech.tasks.domain.enums.TaskPriority;
import com.LIVTech.tasks.domain.enums.TaskStatus;

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
