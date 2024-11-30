package com.ravn.timewars.timer.dao;

import com.ravn.timewars.timer.persistence.TimeEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface TimeEntryDao {

    void createTimeEntry(TimeEntry timeEntry);

    Page<TimeEntry> getAllTimeEntriesByUserId(Long userId, Pageable pageable);

    List<TimeEntry> getAllTimeEntries();

    List<TimeEntry> getAllTimeEntriesByUserId(Long userId);

    TimeEntry getTimeEntryById(Long timeEntryId);

    void updateTimeEntry(TimeEntry timeEntry);
}
