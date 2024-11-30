package com.ravn.timewars.timer.presentation.request;

import java.time.Instant;

public record UpdateTimerRequest(
        Instant startTime,
        Instant endTime,
        String description,
        Long clientId,
        Long projectId
) {
}
