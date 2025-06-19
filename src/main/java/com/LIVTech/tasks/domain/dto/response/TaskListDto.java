package com.LIVTech.tasks.domain.dto.response;

import com.LIVTech.tasks.domain.dto.TaskDto;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record TaskListDto(
    Long id,
    @Size(min = 2,  message = "Title must be 2 or above characters")
    String title,
    String description,
    List<TaskDto> task,
    Integer count,
    Double progress,
    LocalDateTime dueDate

) {
}
