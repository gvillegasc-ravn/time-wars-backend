package com.ravn.timewars.timer.service;

import com.ravn.timewars.timer.dao.ProjectDao;
import com.ravn.timewars.timer.dao.TimeEntryDao;
import com.ravn.timewars.timer.persistence.Project;
import com.ravn.timewars.timer.persistence.TimeEntry;
import com.ravn.timewars.timer.presentation.request.StartTimerRequest;
import com.ravn.timewars.user.dao.ClientDao;
import com.ravn.timewars.user.dao.UserDao;
import com.ravn.timewars.user.persistence.Client;
import com.ravn.timewars.user.persistence.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

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
}
