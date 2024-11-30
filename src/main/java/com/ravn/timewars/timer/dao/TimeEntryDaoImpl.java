package com.ravn.timewars.timer.dao;

import com.ravn.timewars.shared.exception.ResourceNotFoundException;
import com.ravn.timewars.timer.persistence.TimeEntry;
import com.ravn.timewars.timer.persistence.TimeEntryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

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

    @Override
    public List<TimeEntry> getAllTimeEntries() {
        return timeEntryRepository.findAll();
    }

    @Override
    public TimeEntry getTimeEntryById(Long timeEntryId) {
        return timeEntryRepository.findById(timeEntryId).orElseThrow(() -> new ResourceNotFoundException("Time entry not found with id: " + timeEntryId));
    }

    @Override
    public void updateTimeEntry(TimeEntry timeEntry) {
        timeEntryRepository.updateTimeEntry(
                timeEntry.getId(),
                timeEntry.getEndTime(),
                timeEntry.getDescription(),
                timeEntry.getIsRunning(),
                timeEntry.getDuration(),
                timeEntry.getUpdatedAt()
        );
    }


}
