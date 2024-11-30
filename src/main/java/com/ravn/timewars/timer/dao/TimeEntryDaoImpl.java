package com.ravn.timewars.timer.dao;

import com.ravn.timewars.timer.persistence.TimeEntry;
import com.ravn.timewars.timer.persistence.TimeEntryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TimeEntryDaoImpl implements TimeEntryDao {

    private final TimeEntryRepository timeEntryRepository;

    public TimeEntryDaoImpl(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @Override
    public void createTimeEntry(TimeEntry timeEntry) {
        timeEntryRepository.save(timeEntry);
    }

    @Override
    public Page<TimeEntry> getAllTimeEntriesByUserId(Long userId, Pageable pageable) {
        return timeEntryRepository.findAllByUserId(userId, pageable);
    }
}
