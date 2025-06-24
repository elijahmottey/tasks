package com.LIVTech.tasks.repository.specification;

import com.LIVTech.tasks.domain.entities.Task;
import com.LIVTech.tasks.domain.entities.TaskList;
import com.LIVTech.tasks.domain.enums.TaskPriority;
import com.LIVTech.tasks.domain.enums.TaskStatus;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public final class TaskSpecification {

    /**
     * Private constructor to prevent instantiation.
     */
    private TaskSpecification() {}

    /**
     * Creates a specification to find tasks by a keyword in the title or description.
     *
     * @param keyword The keyword to search for.
     * @return A Specification for the query.
     */
    public static Specification<Task> hasKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(keyword)) {
                return criteriaBuilder.conjunction(); // Always true, no-op
            }
            String likePattern = "%" + keyword.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likePattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), likePattern)
            );
        };
    }

    /**
     * Creates a specification to find tasks by their status.
     *
     * @param status The status to filter by.
     * @return A Specification for the query.
     */
    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, criteriaBuilder) ->
                status == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("status"), status);
    }

    /**
     * Creates a specification to find tasks by their priority.
     *
     * @param priority The priority to filter by.
     * @return A Specification for the query.
     */
    public static Specification<Task> hasPriority(TaskPriority priority) {
        return (root, query, criteriaBuilder) ->
                priority == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("priority"), priority);
    }

    /**
     * Creates a specification to find tasks belonging to a specific TaskList.
     *
     * @param taskListId The ID of the TaskList.
     * @return A Specification for the query.
     */
    public static Specification<Task> belongsToTaskList(Long taskListId) {
        return (root, query, criteriaBuilder) -> {
            if (taskListId == null) {
                return criteriaBuilder.conjunction();
            }
            Join<Task, TaskList> taskListJoin = root.join("taskList");
            return criteriaBuilder.equal(taskListJoin.get("id"), taskListId);
        };
    }
}