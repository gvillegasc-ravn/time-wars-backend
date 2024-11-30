package com.ravn.timewars.timer.presentation.request;

import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record StopTimerRequest(
        @NotNull Long timeEntryId,
        @NotNull Timestamp endDate,
        @NotNull String description
        ) {
}
