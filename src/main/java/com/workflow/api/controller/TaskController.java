package com.workflow.api.controller;

import com.workflow.api.dto.AssignTaskRequest;
import com.workflow.api.dto.CreateTaskRequest;
import com.workflow.api.dto.UpdateTaskStatusRequest;
import com.workflow.api.entity.Task;
import com.workflow.api.enums.TaskStatus;
import com.workflow.api.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(request));
    }

    @GetMapping
    public List<Task> findAll(@RequestParam(required = false) TaskStatus status,
                              @RequestParam(required = false) Long userId) {
        if (status != null) {
            return taskService.findByStatus(status);
        }
        if (userId != null) {
            return taskService.findByUser(userId);
        }
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Task findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PatchMapping("/{id}/status")
    public Task updateStatus(@PathVariable Long id,
                             @Valid @RequestBody UpdateTaskStatusRequest request) {
        return taskService.updateStatus(id, request);
    }

    @PatchMapping("/{id}/assign")
    public Task assign(@PathVariable Long id,
                       @Valid @RequestBody AssignTaskRequest request) {
        return taskService.assignUser(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
