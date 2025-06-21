package com.LIVTech.tasks.service;


import com.LIVTech.tasks.domain.entities.Task;
import com.LIVTech.tasks.domain.entities.TaskList;

import java.util.List;

public interface TaskService {
    List<Task> taskLists(Long taskId);
    Task createTask(Long taskListId,Task task);
}
