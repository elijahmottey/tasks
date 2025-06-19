package com.LIVTech.tasks.domain.mapper.impl;

import com.LIVTech.tasks.domain.dto.TaskListDto;
import com.LIVTech.tasks.domain.entities.Task;
import com.LIVTech.tasks.domain.entities.TaskList;
import com.LIVTech.tasks.domain.entities.TaskStatus;
import com.LIVTech.tasks.domain.mapper.TaskListMapper;
import com.LIVTech.tasks.domain.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {

    private  final TaskMapper taskMapper;

    @Autowired
    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }


    @Override
    public TaskListDto taskListToTaskListDto(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::taskToTaskDto)
                                .toList())
                        .orElse(null),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size)
                        .orElse(0),
                calculateProgress(taskList.getTasks()),
                null
        );
    }

    @Override
    public TaskList taskListDtoToTaskList(TaskListDto taskListDto) {
        LocalDateTime now = LocalDateTime.now();
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.task())
                        .map(task -> task
                                .stream()
                                .map(taskMapper::taskDtoToTask)
                                .toList()
                        ).orElse(null),
                null,  // created
                null   // updated
        );
    }

    private Double calculateProgress(List<Task> task){
        if(null == task){
            return null;
        }
       long closedTaskCount = task.stream().filter(tasks -> TaskStatus.CLOSED == tasks.getStatus())
                .count();
        return closedTaskCount / (double)task.size();
    }
}
