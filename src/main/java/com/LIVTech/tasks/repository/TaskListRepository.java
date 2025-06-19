package com.LIVTech.tasks.repository;

import com.LIVTech.tasks.domain.entities.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaskListRepository extends JpaRepository<TaskList,Long> {
}
