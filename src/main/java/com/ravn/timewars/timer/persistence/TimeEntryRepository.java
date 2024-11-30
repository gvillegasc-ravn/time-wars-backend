package com.ravn.timewars.timer.persistence;

import com.ravn.timewars.timer.presentation.response.AllTimeEntriesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {

    Page<TimeEntry> findAllByUserId(Long user_id, Pageable pageable);

    @Modifying
    @Query("update TimeEntry t " +
            "set t.endTime = :end_date, t.description = :description, t.isRunning = :is_running, t.duration = :duration, t.updatedAt = :updated_at " +
            "where t.id = :id")
    void updateTimeEntry(
            @Param(value = "id") Long id,
            @Param(value = "end_date") Instant endTime,
            @Param(value = "description") String description,
            @Param(value = "is_running") Boolean isRunning,
            @Param(value = "duration") Integer duration,
            @Param(value = "updated_at") Instant updatedAt
            );


}