package com.LIVTech.tasks.domain.mapper;

import com.LIVTech.tasks.domain.dto.TaskListDto;
import com.LIVTech.tasks.domain.entities.TaskList;

public interface TaskListMapper {
    //from entity to dto
    TaskListDto taskListToTaskListDto(TaskList taskList);
    // from dto to entity
    TaskList taskListDtoToTaskList(TaskListDto taskListDto);
}
