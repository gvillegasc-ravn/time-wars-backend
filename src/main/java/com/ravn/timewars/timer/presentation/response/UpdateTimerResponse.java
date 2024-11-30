package com.ravn.timewars.timer.presentation.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UpdateTimerResponse {

    private Long id;
    private Instant startTime;
    private Instant endTime;
    private String description;
    private Long clientId;
    private Long projectId;
}
