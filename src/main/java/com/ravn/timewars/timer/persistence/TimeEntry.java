package com.ravn.timewars.timer.persistence;

import com.ravn.timewars.shared.AbstractAuditingEntity;
import com.ravn.timewars.user.persistence.Client;
import com.ravn.timewars.user.persistence.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Table(name = "time_entry")
@Entity
@Builder
public class TimeEntry extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timeEntrySequenceGenerator")
    @SequenceGenerator(name = "timeEntrySequenceGenerator", sequenceName = "timeEntry_sequence", allocationSize = 1, initialValue = 6)
    private Long id;

    private String description;

    private Boolean isBillable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private Instant startTime;

    private Instant endTime;

    private Integer duration;

    private Boolean isRunning;

    private Instant updatedAt;

    private Instant deletedAt;

    public TimeEntry() {
    }

    public TimeEntry(Long id, String description, Boolean isBillable, Project project, User user, Client client, Instant startTime, Instant endTime, Integer duration, Boolean isRunning, Instant updatedAt, Instant deletedAt) {
        this.id = id;
        this.description = description;
        this.isBillable = isBillable;
        this.project = project;
        this.user = user;
        this.client = client;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.isRunning = isRunning;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }
}
