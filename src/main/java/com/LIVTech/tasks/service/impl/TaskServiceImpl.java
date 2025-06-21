package com.LIVTech.tasks.service.impl;

import com.LIVTech.tasks.domain.entities.Task;

import com.LIVTech.tasks.domain.entities.TaskList;
import com.LIVTech.tasks.domain.enums.TaskPriority;
import com.LIVTech.tasks.domain.enums.TaskStatus;
import com.LIVTech.tasks.exception.NotFoundException;
import com.LIVTech.tasks.repository.TaskListRepository;
import com.LIVTech.tasks.repository.TaskRepository;
import com.LIVTech.tasks.service.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskListRepository taskListRepository) {
        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> taskLists(Long taskId) {
        return this.taskRepository.findByTaskListId(taskId);
    }



    @Transactional
    @Override

    public Task createTask(Long taskListId, Task task) {
        if (null != task.getTaskId()) {
            throw new IllegalArgumentException("Task ID must be null for a new task");
        }
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be null or empty");
        }

        TaskList taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new NotFoundException("Task list not found with ID: " + taskListId));

        // Set default values and relationships
        task.setPriority(Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM));
        task.setStatus(TaskStatus.OPEN);
        task.setTaskList(taskList);

        // Set timestamps
        LocalDateTime now = LocalDateTime.now();
        task.setCreated(now);
        task.setUpdated(now);

        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> getTask(Long taskListId, Long taskId) {
        return Optional.of(this.taskRepository.findByTaskListIdAndTaskId(taskListId, taskId)
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + taskId)));
    }


    @Transactional
    @Override

    public Task updateTask(Long taskListId, Long taskId, Task task) {
        if(null == task.getTaskId())
            throw new IllegalArgumentException("Task ID must be null for a new task");

        if(Objects.equals(taskId,task.getTaskId()))
            throw  new IllegalArgumentException("Task IDs do not match");
        if (null == task.getPriority())
            throw new IllegalArgumentException("Task must hava a valid priority");
        if(null == task.getStatus())
            throw new IllegalArgumentException("Task must hava a valid status");

        Optional<Task> existingTask = this.getTask(taskListId,taskId);
        existingTask.get().setTitle(task.getTitle());
        existingTask.get().setDescription(task.getDescription());
        existingTask.get().setDueDate(task.getDueDate());
        existingTask.get().setPriority(task.getPriority());
        existingTask.get().setStatus(task.getStatus());
        existingTask.get().setUpdated(LocalDateTime.now());

        return this.taskRepository.save(existingTask.get());




    }

    @Transactional
    @Override
    public void deleteTask(Long taskListId, Long taskId) {
        this.taskRepository.deleteByTaskListIdAndTaskId(taskListId,taskId);
    }
}
