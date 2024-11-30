package com.ravn.timewars.timer.service;

import com.ravn.timewars.shared.exception.InvalidTimeEntryException;
import com.ravn.timewars.timer.dao.ProjectDao;
import com.ravn.timewars.timer.dao.TimeEntryDao;
import com.ravn.timewars.timer.persistence.ApproveStatus;
import com.ravn.timewars.timer.persistence.Project;
import com.ravn.timewars.timer.persistence.TimeEntry;
import com.ravn.timewars.timer.presentation.request.ApprovedStatusRequest;
import com.ravn.timewars.timer.presentation.request.StartTimerRequest;
import com.ravn.timewars.timer.presentation.request.StopTimerRequest;
import com.ravn.timewars.timer.presentation.request.UpdateTimerRequest;
import com.ravn.timewars.timer.presentation.response.AllTimeEntriesResponse;
import com.ravn.timewars.timer.presentation.response.TimeEntryStopResponse;
import com.ravn.timewars.timer.presentation.response.UpdateTimerResponse;
import com.ravn.timewars.user.dao.ClientDao;
import com.ravn.timewars.user.dao.UserDao;
import com.ravn.timewars.user.persistence.Client;
import com.ravn.timewars.user.persistence.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@Slf4j
public class TimeEntryService {

    private final TimeEntryDao timeEntryDao;
    private final UserDao userDao;
    private final ClientDao clientDao;
    private final ProjectDao projectDao;

    public TimeEntryService(TimeEntryDao timeEntryDao, UserDao userDao, ClientDao clientDao, ProjectDao projectDao) {
        this.timeEntryDao = timeEntryDao;
        this.userDao = userDao;
        this.clientDao = clientDao;
        this.projectDao = projectDao;
    }

    @Transactional
    public void startTimer(StartTimerRequest startTimerRequest) {
        log.info("Starting timer for user with id: {}", startTimerRequest.userId());

        // Get User by id
        User user = userDao.getById(startTimerRequest.userId());
        log.info("User name: {}", user.getName());
        // Get Client by id
        Client client = clientDao.getById(startTimerRequest.clientId());
        log.info("Client name: {}", client.getName());
        // Get Project by id
        Project project = projectDao.getById(startTimerRequest.projectId());
        log.info("Project name: {}", project.getName());

        // Create TimeEntry
        TimeEntry timeEntry = new TimeEntry();
        timeEntry.setUser(user);
        timeEntry.setClient(client);
        timeEntry.setProject(project);

        // Set start time with start time from request
        timeEntry.setStartTime(Instant.parse(startTimerRequest.startDate().toInstant().toString()));
        timeEntry.setIsRunning(true);

        log.info("TimeEntry start time: {}", timeEntry.getStartTime());
        // Save TimeEntry
        timeEntryDao.createTimeEntry(timeEntry);
    }

    @Transactional
    public TimeEntryStopResponse stopTimer(StopTimerRequest stopTimerRequest) {
        log.info("Stopping timer for user with id: {}", stopTimerRequest.timeEntryId());

        // Get TimeEntry by id
        TimeEntry timeEntryToUpdate = timeEntryDao.getTimeEntryById(stopTimerRequest.timeEntryId());
        log.info("TimeEntry start time to update: {}", timeEntryToUpdate.getStartTime());

        // Set end time with end time from request
        timeEntryToUpdate.setEndTime(Instant.parse(stopTimerRequest.endDate().toInstant().toString()));
        timeEntryToUpdate.setDescription(stopTimerRequest.description());
        timeEntryToUpdate.setIsRunning(false);

        // Calculate duration
        Duration duration = Duration.between(timeEntryToUpdate.getStartTime(), timeEntryToUpdate.getEndTime());
        log.info("TimeEntry duration in minutes: {}", duration.toMinutes());
        timeEntryToUpdate.setDuration((int) duration.toMinutes());

        // Save TimeEntry
        timeEntryDao.updateTimeEntry(timeEntryToUpdate);

        return TimeEntryStopResponse.builder()
                .id(timeEntryToUpdate.getId())
                .description(stopTimerRequest.description())
                .build();
    }

    public List<AllTimeEntriesResponse> getAllTimeEntries() {
        log.info("Getting all time entries");

        List<TimeEntry> timeEntries = timeEntryDao.getAllTimeEntries();

        log.info("Time entries size: {}", timeEntries.size());

        return timeEntries.stream()
                .map(this::toAllTimeEntriesResponse)
                .toList();
    }

    // Mapping method
    private AllTimeEntriesResponse toAllTimeEntriesResponse(TimeEntry timeEntry) {
        AllTimeEntriesResponse response = new AllTimeEntriesResponse();

        response.setId(timeEntry.getId());
        response.setStartTime(timeEntry.getStartTime());
        response.setEndTime(timeEntry.getEndTime());
        response.setDescription(timeEntry.getDescription());

        // Get client
        Client client = clientDao.getById(timeEntry.getClient().getId());
        // Get project
        Project project = projectDao.getById(timeEntry.getProject().getId());

        // Get user
        User user = userDao.getById(timeEntry.getUser().getId());

        response.setClientName(client.getName());
        response.setProjectName(project.getName());
        response.setUserId(user.getId());
        response.setUserName(user.getName());
        response.setApprovedStatus(timeEntry.getApproveStatus().name());

        return response;
    }

    @Transactional
    public List<AllTimeEntriesResponse> changeApprovedStatus(@Valid ApprovedStatusRequest approveTimeEntryRequest) {
        log.info("Changing approved status for time entries from user with id: {}", approveTimeEntryRequest.userId());

        // Get TimeEntries by user id
        List<TimeEntry> timeEntries = timeEntryDao.getAllTimeEntriesByUserId(approveTimeEntryRequest.userId());

        log.info("Time entries size: {}", timeEntries.size());

        // Change approved status for each time entry
        timeEntries.forEach(timeEntry -> {
            if (approveTimeEntryRequest.approvedStatus() == 1) {
                timeEntry.setApproveStatus(ApproveStatus.APPROVED);
            } else {
                timeEntry.setApproveStatus(ApproveStatus.REJECTED);
            }
            timeEntryDao.updateTimeEntry(timeEntry);
        });

        return timeEntries.stream()
                .map(this::toAllTimeEntriesResponse)
                .toList();
    }

    @Transactional
    public UpdateTimerResponse updateTimeEntry(Long id, UpdateTimerRequest updateTimerRequest) {

        log.info("Updating time entry with TimerId: {}", id);

        // Get TimeEntry by id
        TimeEntry timeEntryToUpdate = timeEntryDao.getTimeEntryById(id);

        // Update fields if not null in the request
        updateTimeEntryField(timeEntryToUpdate, updateTimerRequest);

        // Calculate duration
        Duration duration = Duration.between(timeEntryToUpdate.getStartTime(), timeEntryToUpdate.getEndTime());
        if (duration.isNegative()) {
            log.error("End time is earlier than start time for TimeEntry id: {}", id);
            throw new InvalidTimeEntryException("End time cannot be earlier than start time");
        }
        log.info("TimeEntry duration in minutes is: {}", duration.toMinutes());
        timeEntryToUpdate.setDuration((int) duration.toMinutes());

        // Set isRunning flag and isManual flag
        timeEntryToUpdate.setIsRunning(false);
        if (updateTimerRequest.startTime() != null && updateTimerRequest.endTime() != null) {
            log.info("The time entry is manual with id: {}", id);
            timeEntryToUpdate.setIsManual(true);
        }

        // Save TimeEntry
        timeEntryDao.updateTimeEntry(timeEntryToUpdate);

        return buildUpdateTimerResponse(timeEntryToUpdate);
    }

    private void updateTimeEntryField(TimeEntry timeEntry, UpdateTimerRequest request) {
        if (request.startTime() != null) {
            timeEntry.setStartTime(request.startTime());
        }
        if (request.endTime() != null) {
            timeEntry.setEndTime(request.endTime());
        }
        if (request.description() != null) {
            timeEntry.setDescription(request.description());
        }
        if (request.clientId() != null) {
            Client client = clientDao.getById(request.clientId());
            timeEntry.setClient(client);
        }
        if (request.projectId() != null) {
            Project project = projectDao.getById(request.projectId());
            timeEntry.setProject(project);
        }
    }

    private UpdateTimerResponse buildUpdateTimerResponse(TimeEntry timeEntry) {
        return UpdateTimerResponse.builder()
                .id(timeEntry.getId())
                .startTime(timeEntry.getStartTime())
                .endTime(timeEntry.getEndTime())
                .description(timeEntry.getDescription())
                .clientId(timeEntry.getClient().getId())
                .projectId(timeEntry.getProject().getId())
                .build();
    }
}
