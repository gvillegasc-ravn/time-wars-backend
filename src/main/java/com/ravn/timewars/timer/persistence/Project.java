package com.ravn.timewars.timer.persistence;

import com.ravn.timewars.shared.AbstractAuditingEntity;
import com.ravn.timewars.user.persistence.Client;
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

@Getter
@Setter
@Table(name = "project")
@Entity
@Builder
public class Project extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "projectSequenceGenerator")
    @SequenceGenerator(name = "projectSequenceGenerator", sequenceName = "project_sequence", allocationSize = 1, initialValue = 1)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private Boolean billable;

    private Boolean isDefault = false;

    public Project() {
    }

    public Project(Long id, String name, Client client, Boolean billable, Boolean isDefault) {
        this.id = id;
        this.name = name;
        this.client = client;
        this.billable = billable;
        this.isDefault = isDefault;
    }
}
