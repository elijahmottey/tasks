package com.LIVTech.tasks.service.impl;

import com.LIVTech.tasks.domain.dto.TaskListDto;
import com.LIVTech.tasks.domain.entities.TaskList;
import com.LIVTech.tasks.repository.TaskListRepository;
import com.LIVTech.tasks.service.TaskListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;

   @Autowired
    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
       if(null != taskList.getId())
           throw new IllegalArgumentException("Task list already exists");
       if(null == taskList.getTitle() || taskList.getTitle().isBlank())
           throw new IllegalArgumentException("Task list title cannot be null or empty");
        LocalDateTime now = LocalDateTime.now();
        taskList.setCreated(now);
        taskList.setUpdated(now);
       return taskListRepository.save(taskList);

    }
}
