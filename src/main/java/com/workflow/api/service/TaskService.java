package com.workflow.api.service;

import com.workflow.api.dto.AssignTaskRequest;
import com.workflow.api.dto.CreateTaskRequest;
import com.workflow.api.dto.UpdateTaskStatusRequest;
import com.workflow.api.entity.Task;
import com.workflow.api.entity.User;
import com.workflow.api.enums.TaskStatus;
import com.workflow.api.exception.ResourceNotFoundException;
import com.workflow.api.repository.TaskRepository;
import com.workflow.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Task create(CreateTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setPriority(request.priority());
        task.setDueDate(request.dueDate());
        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public List<Task> findByUser(Long userId) {
        return taskRepository.findByAssignedUserId(userId);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public Task updateStatus(Long id, UpdateTaskStatusRequest request) {
        Task task = findById(id);
        task.setStatus(request.status());
        return taskRepository.save(task);
    }

    public Task assignUser(Long id, AssignTaskRequest request) {
        Task task = findById(id);
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.userId()));
        task.setAssignedUser(user);
        return taskRepository.save(task);
    }

    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }
}
