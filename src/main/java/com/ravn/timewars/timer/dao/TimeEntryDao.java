package com.ravn.timewars.timer.dao;

import com.ravn.timewars.timer.persistence.TimeEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TimeEntryDao {

    void createTimeEntry(TimeEntry timeEntry);

    Page<TimeEntry> getAllTimeEntriesByUserId(Long userId, Pageable pageable);
}
