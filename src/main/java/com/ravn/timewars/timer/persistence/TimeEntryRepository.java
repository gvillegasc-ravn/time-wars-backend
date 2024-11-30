package com.ravn.timewars.timer.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {

    Page<TimeEntry> findAllByUserId(Long user_id, Pageable pageable);
}