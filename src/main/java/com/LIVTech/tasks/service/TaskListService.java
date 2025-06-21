package com.LIVTech.tasks.service;


import com.LIVTech.tasks.domain.entities.TaskList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface TaskListService {
    List<TaskList> listTaskLists();
    TaskList createTaskList(TaskList taskList);
    TaskList getTaskListById(Long id);
    TaskList updateTaskList(Long id, TaskList taskList);
    void deleteTaskList(Long id);

}
