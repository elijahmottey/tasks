package com.LIVTech.tasks.repository;

import com.LIVTech.tasks.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
   List<Task> findByTaskListId(Long taskId);
    //Optional<Task> findByTaskListIdAndId(Long taskListId, Long id);

}
