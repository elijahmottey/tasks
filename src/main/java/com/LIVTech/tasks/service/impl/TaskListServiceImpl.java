package com.LIVTech.tasks.service.impl;

import com.LIVTech.tasks.domain.entities.TaskList;
import com.LIVTech.tasks.exception.NotFoundException;
import com.LIVTech.tasks.repository.TaskListRepository;
import com.LIVTech.tasks.service.TaskListService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the TaskListService interface that provides operations for managing task lists.
 * This service handles CRUD operations for TaskList entities using Spring Data JPA.
 * @author Elijah
 */



@Service
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;


    /**
     * Constructs a new TaskListServiceImpl with the required repository.
     *
     * @param taskListRepository The repository for TaskList entities
     */


    @Autowired
    public TaskListServiceImpl(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }


    /**
     * Retrieves all task lists from the database.
     *
     * @return List of all TaskList entities
     */

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }


    /**
     * Creates a new task list in the database.
     *
     * @param taskList The TaskList entity to be created
     * @return The saved TaskList entity with generated ID and timestamps
     * @throws IllegalArgumentException if the task list already has an ID or if the title is null or empty
     */


    @Override
    public TaskList createTaskList(TaskList taskList) {
       if(null != taskList.getId())
           throw new IllegalArgumentException("Task list already exists");
       if(null == taskList.getTitle() || taskList.getTitle().isBlank())
           throw new IllegalArgumentException("Task list title cannot be null or empty");
        LocalDateTime now = LocalDateTime.now();
        taskList.setCreated(now);
        taskList.setUpdated(now);
       return this.taskListRepository.save(taskList);

    }

    /**
     * Retrieves a task list by its ID.
     *
     * @param id The ID of the task list to retrieve
     * @return The TaskList entity if found
     * @throws IllegalArgumentException if the provided ID is null or less than or equal to 0
     * @throws NotFoundException if no task list is found with the given ID
     */


    @Override
    public TaskList getTaskListById(Long id) {
       if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid task list ID: " + id);
        }
        return this.taskListRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Task list not found with id: " + id));
    }


    @Transactional
    @Override

    public TaskList updateTaskList(Long id, TaskList taskList) {

        if(!Objects.equals(taskList.getId(),id))
            throw new IllegalArgumentException("Task list ID in the request does not match the provided ID");

       TaskList existingTaskList = this.getTaskListById(id);

        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setTasks(taskList.getTasks());
        existingTaskList.setUpdated(LocalDateTime.now());
        return this.taskListRepository.save(existingTaskList);


    }



    @Transactional
    @Override

    public void deleteTaskList(Long id) {
        this.taskListRepository.deleteById(id);


    }


}
