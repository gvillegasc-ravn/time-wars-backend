package com.ravn.timewars.timer.presentation.response;

import lombok.Data;

import java.time.Instant;

@Data
public class AllTimeEntriesResponse {

    private Long id;
    private Instant startTime;
    private Instant endTime;
    private String description;
    private String clientName;
    private String projectName;
}
