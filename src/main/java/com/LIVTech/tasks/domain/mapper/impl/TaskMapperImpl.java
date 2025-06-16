package com.LIVTech.tasks.domain.mapper.impl;

import com.LIVTech.tasks.domain.dto.TaskDto;
import com.LIVTech.tasks.domain.entities.Task;
import com.LIVTech.tasks.domain.mapper.TaskMapper;

import java.time.LocalDateTime;

import static java.time.LocalTime.now;

public  class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDto taskToTaskDto(Task task) {
        return new TaskDto(
            task.getTaskId(),
            task.getTitle(),
            task.getDescription(),
            task.getDueDate(),
            task.getPriority(),
            task.getStatus()
        );
    }


    @Override
    public Task taskDtoToTask(TaskDto taskDto) {
        LocalDateTime now = LocalDateTime.now();
        return new Task(
                        taskDto.id(),
                        taskDto.title(),
                        taskDto.description(),
                        taskDto.dueDate(),
                taskDto.status(),
                taskDto.priority(),
                        null, // taskList
                        now,  // created
                        now   // updated
                );
    }
}
