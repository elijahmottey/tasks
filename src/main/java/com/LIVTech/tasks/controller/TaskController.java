package com.LIVTech.tasks.controller;


import com.LIVTech.tasks.domain.dto.TaskDto;
import com.LIVTech.tasks.domain.dto.response.ApiResponse;
import com.LIVTech.tasks.domain.entities.Task;
import com.LIVTech.tasks.domain.enums.TaskPriority;
import com.LIVTech.tasks.domain.enums.TaskStatus;
import com.LIVTech.tasks.domain.mapper.TaskMapper;
import com.LIVTech.tasks.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @PutMapping("/update/{taskId}")
    public ResponseEntity<ApiResponse<TaskDto>> updateTask(
            @PathVariable("listId") Long listId,
            @PathVariable("taskId") Long taskId,
            @RequestBody @Valid TaskDto taskDto
    ) {
        Task updatedTask = taskService.updateTask(listId,
                taskId,
                taskMapper.taskDtoToTask(taskDto));
        return ResponseEntity.ok(new ApiResponse<>(taskMapper.taskToTaskDto(updatedTask),
                "Task with ID  "+taskId+" updated successfully"));


}

 @DeleteMapping("/delete/{taskId}")
    public void deleteTask(@PathVariable("listId") Long listId, @PathVariable("taskId") Long taskId) {
        this.taskService.deleteTask(listId,taskId);
        ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping
    public ResponseEntity<ApiResponse<org.springframework.data.domain.Page<TaskDto>>> searchAndListTasks(
            @PathVariable("taskListId") Long taskListId, // The list ID now comes consistently from the path
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            Pageable pageable) {

        // The service is called with the taskListId from the path
        org.springframework.data.domain.Page<Task> taskPage = (org.springframework.data.domain.Page<Task>) taskService
                .searchTasks(taskListId, keyword, status, priority, pageable);

        // Map the Page<Task> to Page<TaskDto>
        Page<TaskDto> taskDtoPage = taskPage.map(taskMapper::taskToTaskDto);

        return ResponseEntity.ok(new ApiResponse<>(taskDtoPage, "Tasks retrieved successfully"));
    }






}
