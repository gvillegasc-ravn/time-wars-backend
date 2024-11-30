package com.ravn.timewars.timer.presentation.request;

import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record StartTimerRequest(
        @NotNull Timestamp startDate,
        @NotNull Long userId,
        @NotNull Long clientId,
        @NotNull Long projectId
) {
}
