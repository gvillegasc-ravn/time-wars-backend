package com.ravn.timewars.timer.presentation;

import com.ravn.timewars.shared.ResponseHandler;
import com.ravn.timewars.timer.presentation.request.StartTimerRequest;
import com.ravn.timewars.timer.service.TimeEntryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/time-entries")
public class TimeEntryController {

    private final TimeEntryService timeEntryService;

    public TimeEntryController(TimeEntryService timeEntryService) {
        this.timeEntryService = timeEntryService;
    }

    @PostMapping("/start-timer")
    public ResponseEntity<Void> startTimer(@RequestBody @Valid StartTimerRequest startTimerRequest) {
        timeEntryService.startTimer(startTimerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
