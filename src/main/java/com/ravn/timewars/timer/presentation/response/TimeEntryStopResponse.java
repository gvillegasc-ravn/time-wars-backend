package com.ravn.timewars.timer.presentation.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeEntryStopResponse {
    private Long id;
    private String description;
}
