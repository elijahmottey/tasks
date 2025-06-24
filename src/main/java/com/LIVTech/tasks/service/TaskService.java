package com.LIVTech.tasks.service;

import com.LIVTech.tasks.domain.entities.Task;
import com.LIVTech.tasks.domain.enums.TaskPriority;
import com.LIVTech.tasks.domain.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing Tasks.
 * This defines the contract for all task-related business operations,
 * including CRUD and advanced search functionality.
 */
public interface TaskService {

    /**
     * Searches for tasks based on a combination of criteria with pagination support.
     *
     * @param taskListId The ID of the TaskList to search within.
     * @param keyword    A keyword to search for in the task's title and description.
     * @param status     The status to filter by (e.g., OPEN, IN_PROGRESS).
     * @param priority   The priority to filter by (e.g., HIGH, LOW).
     * @param pageable   Pagination and sorting information.
     * @return A paginated list of tasks matching the criteria.
     */
    // FIX 2: Added a semicolon to separate this method signature from the next one.
    Page<Task> searchTasks(Long taskListId, String keyword, TaskStatus status, TaskPriority priority, Pageable pageable);

    /**
     * Retrieves all tasks belonging to a specific task list.
     *
     * @param taskId The ID of the task list (Note: parameter name is 'taskId' per request).
     * @return A list of all tasks for the given task list.
     */
    List<Task> taskLists(Long taskId);

    /**
     * Creates a new task and associates it with a specific task list.
     *
     * @param taskListId The ID of the parent TaskList.
     * @param task       The task entity to create.
     * @return The newly created and saved Task.
     */
    Task createTask(Long taskListId, Task task);

    /**
     * Retrieves a single task by its ID, ensuring it belongs to the specified task list.
     *
     * @param taskListId The ID of the parent TaskList.
     * @param taskId     The ID of the task to retrieve.
     * @return An Optional containing the found Task, or an empty Optional if not found.
     */
    Optional<Task> getTask(Long taskListId, Long taskId);

    /**
     * Updates an existing task.
     *
     * @param taskListId The ID of the parent TaskList.
     * @param taskId     The ID of the task to update.
     * @param task       The task entity containing the updated details.
     * @return The updated Task.
     */
    Task updateTask(Long taskListId, Long taskId, Task task);

    /**
     * Deletes a task by its ID.
     *
     * @param taskListId The ID of the parent TaskList.
     * @param taskId     The ID of the task to delete.
     */
    void deleteTask(Long taskListId, Long taskId);
}