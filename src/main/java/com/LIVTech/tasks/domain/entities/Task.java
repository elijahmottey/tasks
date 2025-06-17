package com.LIVTech.tasks.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")

public class Task  {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "taskId",nullable = false)
    private Long taskId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private  String description;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    //@Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = false)
    private TaskStatus status;

    @Column(name = "priority", nullable = false)
    private  TaskPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_list-id")
    private  TaskList taskList;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;



}
