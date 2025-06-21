package com.LIVTech.tasks.controller;


import com.LIVTech.tasks.domain.dto.TaskDto;
import com.LIVTech.tasks.domain.dto.response.ApiResponse;
import com.LIVTech.tasks.domain.mapper.TaskMapper;
import com.LIVTech.tasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/task-list/{listId}/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskDto>> >getTaskList(@PathVariable("listId") Long listId) {
        List<TaskDto> tasks = taskService.taskLists(listId)
                .stream()
                .map(taskMapper::taskToTaskDto)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(tasks, "Tasks retrieved successfully"));
    }
}
