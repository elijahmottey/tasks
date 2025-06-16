package com.LIVTech.tasks.domain.mapper;

import com.LIVTech.tasks.domain.dto.TaskDto;
import com.LIVTech.tasks.domain.entities.Task;

public interface TaskMapper {
    TaskDto taskToTaskDto(Task task);
    Task taskDtoToTask(TaskDto taskDto);
}
