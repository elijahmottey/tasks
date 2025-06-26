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

/**
 * Controller to handle API requests for Tasks, which are nested under a TaskList.
 * The base path for all endpoints in this controller is /task-list/{listId}.
 */
@RestController
@RequestMapping("/task-list/{listId}")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    /**
     * Constructs a TaskController with necessary service and mapper dependencies.
     *
     * @param taskService The service for task-related business logic.
     * @param taskMapper  The mapper for converting between Task entities and DTOs.
     */
    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }



    /**
     * Retrieves a non-paginated list of all tasks associated with a specific task list.
     * Note: This endpoint is less flexible than the root search endpoint.
     *
     * @param listId The ID of the task list to retrieve tasks from.
     * @return A ResponseEntity containing an ApiResponse with a list of TaskDto objects.
     */
    @GetMapping("get-tasks")
    public ResponseEntity<ApiResponse<List<TaskDto>> >getTaskList(@PathVariable("listId") Long listId) {
        List<TaskDto> tasks = taskService.taskLists(listId)
                .stream()
                .map(taskMapper::taskToTaskDto)
                .toList();
        return ResponseEntity.ok(new ApiResponse<>(tasks, "Tasks retrieved successfully"));
    }

    /**
     * Creates a new task within the specified task list.
     *
     * @param listId  The ID of the task list where the new task will be created.
     * @param taskDto The DTO containing the details of the task to create.
     * @return A ResponseEntity with the created TaskDto and an HTTP status of 201 (Created).
     */
    @PostMapping("/create-task")
    public ResponseEntity<ApiResponse<TaskDto>> createTask(@PathVariable("listId") Long listId, @RequestBody @Valid TaskDto taskDto) {
        Task  createdTasks = this.taskService.createTask(listId,taskMapper.taskDtoToTask(taskDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(this.taskMapper.taskToTaskDto(createdTasks),"Task created successfully"));
    }

    /**
     * Retrieves a single task by its ID, scoped to a specific task list.
     * Note: This method returns an Optional within the response body, which is not a standard REST practice.
     * A client will receive a 200 OK even if the task is not found.
     *
     * @param taskId The ID of the task to retrieve.
     * @param listId The ID of the parent task list.
     * @return A ResponseEntity containing an ApiResponse with an Optional<TaskDto>.
     */
    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Optional<TaskDto>>> getTask(
            @PathVariable("taskId") Long taskId,
            @PathVariable("listId") Long listId
    ) {
        return new ResponseEntity<>(new ApiResponse<>(this.taskService.getTask(listId,taskId)
                .map(taskMapper::taskToTaskDto),
                "Task retrieved successfully"), HttpStatus.OK);
    }

    /**
     * Updates an existing task identified by its ID and its parent task list ID.
     *
     * @param listId  The ID of the parent task list.
     * @param taskId  The ID of the task to update.
     * @param taskDto The DTO containing the updated task details.
     * @return A ResponseEntity with the updated TaskDto.
     */
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

    /**
     * Deletes a specific task from a task list.
     * Note: This method has a 'void' return type and does not correctly send the 204 No Content status.
     *
     * @param listId The ID of the parent task list.
     * @param taskId The ID of the task to delete.
     */
    @DeleteMapping("/delete/{taskId}")
    public void deleteTask(@PathVariable("listId") Long listId, @PathVariable("taskId") Long taskId) {
        this.taskService.deleteTask(listId,taskId);
        ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * Searches for tasks within a specific task list based on various filter criteria, with pagination.
     * If no parameters are provided, it returns a paginated list of all tasks.
     * CRITICAL BUG: The @PathVariable("taskListId") does not match the class-level @RequestMapping("/task-list/{listId}").
     *
     * @param taskListId The ID of the task list to search within.
     * @param keyword    An optional keyword to search for in the task's title or description.
     * @param status     An optional status to filter by (e.g., OPEN, IN_PROGRESS).
     * @param priority   An optional priority to filter by (e.g., HIGH, LOW).
     * @param pageable   Pagination and sorting information.
     * @return A ResponseEntity containing a paginated list of matching TaskDtos.
     */
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