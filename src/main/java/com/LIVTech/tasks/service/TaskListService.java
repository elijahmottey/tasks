package com.LIVTech.tasks.service;


import com.LIVTech.tasks.domain.entities.TaskList;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TaskListService {
    List<TaskList> listTaskLists();
    TaskList createTaskList(TaskList taskList);

}
