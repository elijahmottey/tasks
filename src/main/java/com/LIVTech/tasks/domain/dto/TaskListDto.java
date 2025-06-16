package com.LIVTech.tasks.domain.dto;

import com.LIVTech.tasks.domain.entities.TaskPriority;
import com.LIVTech.tasks.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

public record TaskListDto(
    Long id,
    String title,
    String description,
    Integer count,
    Double progress,
    LocalDateTime dueDate,
    List<TaskDto> task
) {
}
