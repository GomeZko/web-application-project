package com.workflow.api.dto;

import jakarta.validation.constraints.NotNull;

public record AssignTaskRequest(

        @NotNull(message = "User ID is required")
        Long userId
) {}
