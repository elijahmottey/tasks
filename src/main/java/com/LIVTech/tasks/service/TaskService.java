package com.LIVTech.tasks.service;


import com.LIVTech.tasks.domain.entities.Task;
import com.LIVTech.tasks.domain.entities.TaskList;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> taskLists(Long taskId);
    Task createTask(Long taskListId,Task task);
    Optional<Task> getTask(Long taskListId, Long taskId);
    Task updateTask(Long taskListId, Long taskId,Task task);
    void deleteTask(Long taskListId, Long taskId);
}
