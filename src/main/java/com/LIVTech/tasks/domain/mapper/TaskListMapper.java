package com.LIVTech.tasks.domain.mapper;

import com.LIVTech.tasks.domain.dto.TaskListDto;
import com.LIVTech.tasks.domain.entities.TaskList;

public interface TaskListMapper {
    TaskListDto taskListToTaskList(TaskList taskList);
    TaskList taskListToTaskListDto(TaskListDto taskListDto);
}
