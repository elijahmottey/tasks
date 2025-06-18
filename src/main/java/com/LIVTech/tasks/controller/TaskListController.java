package com.LIVTech.tasks.controller;


import com.LIVTech.tasks.domain.dto.TaskListDto;
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
    public ResponseEntity<List<TaskListDto>> listTaskLists() {
        return ResponseEntity.ok(
                taskListService.listTaskLists()
                        .stream()
                        .map(taskListMapper::taskListDtoToTaskList)
                        .toList());

    }

    @PostMapping("/create")
    public ResponseEntity<TaskListDto> createTaskList( @RequestBody @Valid TaskListDto taskListDto) {
        TaskList createTaskList =taskListService.createTaskList(taskListMapper.taskListToTaskListDto(taskListDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(taskListMapper.taskListDtoToTaskList(createTaskList));

    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskListDto> getTaskList(@PathVariable("taskId") Long taskId) {
        TaskList taskList = taskListService.getTaskListById(taskId);
        return ResponseEntity.ok(taskListMapper.taskListDtoToTaskList(taskList));
    }


}
