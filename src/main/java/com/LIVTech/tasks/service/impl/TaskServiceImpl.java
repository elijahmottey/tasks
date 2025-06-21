package com.LIVTech.tasks.service.impl;

import com.LIVTech.tasks.domain.entities.Task;

import com.LIVTech.tasks.repository.TaskRepository;
import com.LIVTech.tasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> taskLists(Long taskId) {
        return this.taskRepository.findByTaskListId(taskId);
    }
}
