package com.LIVTech.tasks.controller;


import com.LIVTech.tasks.domain.dto.TaskDto;
import com.LIVTech.tasks.domain.dto.response.ApiResponse;
import com.LIVTech.tasks.domain.entities.Task;
import com.LIVTech.tasks.domain.mapper.TaskMapper;
import com.LIVTech.tasks.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task-list/{listId}")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping("get-tasks")
    public ResponseEntity<ApiResponse<List<TaskDto>> >getTaskList(@PathVariable("listId") Long listId) {
        List<TaskDto> tasks = taskService.taskLists(listId)
                .stream()
                .map(taskMapper::taskToTaskDto)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(tasks, "Tasks retrieved successfully"));
    }

    @PostMapping("/create-task")
    public ResponseEntity<ApiResponse<TaskDto>> createTask(@PathVariable("listId") Long listId, @RequestBody @Valid TaskDto taskDto) {
        Task  createdTasks = this.taskService.createTask(listId,taskMapper.taskDtoToTask(taskDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(this.taskMapper.taskToTaskDto(createdTasks),"Task created successfully"));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Optional<TaskDto>>> getTask(
            @PathVariable("taskId") Long taskId,
            @PathVariable("listId") Long listId
    ) {
        return new ResponseEntity<>(new ApiResponse<>(this.taskService.getTask(listId,taskId)
                .map(taskMapper::taskToTaskDto),
                "Task retrieved successfully"), HttpStatus.OK);
    }




}
