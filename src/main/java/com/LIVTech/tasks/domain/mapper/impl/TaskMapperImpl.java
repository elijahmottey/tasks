package com.LIVTech.tasks.domain.mapper.impl;

import com.LIVTech.tasks.domain.dto.TaskDto;
import com.LIVTech.tasks.domain.entities.Task;
import com.LIVTech.tasks.domain.mapper.TaskMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
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
       // LocalDateTime now = LocalDateTime.now();
        return new Task(
                        taskDto.id(),
                        taskDto.title(),
                        taskDto.description(),
                        taskDto.dueDate(),
                taskDto.status(),
                taskDto.priority(),
                        null, // taskList
                        null,  // created
                        null   // updated
                );
    }
}
