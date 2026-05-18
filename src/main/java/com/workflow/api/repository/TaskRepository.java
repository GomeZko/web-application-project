package com.workflow.api.repository;

import com.workflow.api.entity.Task;
import com.workflow.api.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByAssignedUserId(Long userId);
}
