package com.LIVTech.tasks.controller;

import com.LIVTech.tasks.domain.dto.TaskDto;
import com.LIVTech.tasks.domain.dto.response.ApiResponse;
import com.LIVTech.tasks.domain.entities.Task;
import com.LIVTech.tasks.domain.enums.TaskPriority;
import com.LIVTech.tasks.domain.enums.TaskStatus;
import com.LIVTech.tasks.domain.mapper.TaskMapper;
import com.LIVTech.tasks.exception.NotFoundException;
import com.LIVTech.tasks.service.TaskService;
import jakarta.validation.Valid;
// FIX 1: Correct import for Spring Data's Page interface
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-lists/{taskListId}/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

   @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    /**
     *  Consolidated endpoint for listing and searching tasks within a specific task list.
     * This single endpoint replaces the previous `/get-tasks` and `/search`.
     * It supports filtering by keyword, status, and priority, as well as pagination.
     * If no request parameters are provided, it returns a paginated list of all tasks.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<TaskDto>>> searchAndListTasks(
            @PathVariable("taskListId") Long taskListId, // The list ID now comes consistently from the path
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            Pageable pageable) {

        // The service is called with the taskListId from the path
        Page<Task> taskPage = (Page<Task>) taskService
                .searchTasks(taskListId, keyword, status, priority, pageable);

        // Map the Page<Task> to Page<TaskDto>
        Page<TaskDto> taskDtoPage = taskPage.map(taskMapper::taskToTaskDto);

        return ResponseEntity.ok(new ApiResponse<>(taskDtoPage, "Tasks retrieved successfully"));
    }

    /**
     * Creates a new task within the specified task list.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<TaskDto>> createTask(
            @PathVariable("taskListId") Long taskListId,
            @RequestBody @Valid TaskDto taskDto) {
        Task taskToCreate = taskMapper.taskDtoToTask(taskDto);
        Task createdTask = this.taskService.createTask(taskListId, taskToCreate);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(this.taskMapper.taskToTaskDto(createdTask), "Task created successfully"));
    }

    /**
     * Retrieves a list of tasks within a specific task list.
     * Retrieves a single task by its ID within a specific task list.
     * Returns a 404 Not Found if the task doesn't exist.
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskDto>> getTask(
            @PathVariable("taskListId") Long taskListId,
            @PathVariable("taskId") Long taskId
    ) {
        TaskDto taskDto = this.taskService.getTask(taskListId, taskId)
                .map(taskMapper::taskToTaskDto)
                .orElseThrow(() -> new NotFoundException("Task not found with id: " + taskId));

        return ResponseEntity.ok(new ApiResponse<>(taskDto, "Task retrieved successfully"));
    }

    /**
     * Updates an existing task.
     */
    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskDto>> updateTask(
            @PathVariable("taskListId") Long taskListId,
            @PathVariable("taskId") Long taskId,
            @RequestBody @Valid TaskDto taskDto
    ) {
        Task taskToUpdate = taskMapper.taskDtoToTask(taskDto);
        Task updatedTask = taskService.updateTask(taskListId, taskId, taskToUpdate);
        return ResponseEntity.ok(new ApiResponse<>(taskMapper.taskToTaskDto(updatedTask),
                "Task with ID " + taskId + " updated successfully"));
    }

    /**
     *
     * Deletes a task by its ID.
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable("taskListId") Long taskListId,
            @PathVariable("taskId") Long taskId) {
        this.taskService.deleteTask(taskListId, taskId);
        return ResponseEntity.noContent().build(); // This correctly builds and returns the response
    }
}