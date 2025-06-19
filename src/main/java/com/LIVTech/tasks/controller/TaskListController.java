package com.LIVTech.tasks.controller;


import com.LIVTech.tasks.domain.dto.response.TaskListDto;
import com.LIVTech.tasks.domain.dto.response.ApiResponse;
import com.LIVTech.tasks.domain.entities.TaskList;
import com.LIVTech.tasks.domain.mapper.TaskListMapper;
import com.LIVTech.tasks.service.TaskListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/tasklists")
public class TaskListController {

    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    @Autowired
    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }


    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<TaskListDto>>> listTaskLists() {

             List<TaskListDto> tasks =  taskListService.listTaskLists()
                        .stream()
                        .map(taskListMapper::taskListToTaskListDto)
                        .toList();
             return ResponseEntity.ok(new ApiResponse<>(tasks, "Task lists retrieved successfully"));

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TaskListDto>>createTaskList( @RequestBody @Valid TaskListDto taskListDto) {
        TaskList createTaskList =taskListService.createTaskList(taskListMapper.taskListDtoToTaskList(taskListDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(taskListMapper.taskListToTaskListDto(createTaskList),
                "Task list created successfully"));

    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskListDto>> getTaskList(@PathVariable("taskId") Long taskId) {
        TaskList taskList = taskListService.getTaskListById(taskId);
        return ResponseEntity.ok(new ApiResponse<>(taskListMapper.taskListToTaskListDto(taskList),"Task with "+taskId+" retrieved successfully"));
    }


}
