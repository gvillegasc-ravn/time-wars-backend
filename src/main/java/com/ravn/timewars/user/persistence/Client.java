package com.ravn.timewars.user.persistence;

import com.ravn.timewars.shared.AbstractAuditingEntity;
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
@Table(name = "client")
@Entity
@Builder
public class Client extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientSequenceGenerator")
    @SequenceGenerator(name = "clientSequenceGenerator", sequenceName = "client_sequence", allocationSize = 1, initialValue = 6)
    private Long id;

    private String name;

    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Client() {
    }

    public Client(Long id, String name, Boolean isActive, User user) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
        this.user = user;
    }
}
