package com.ravn.timewars.user.persistence;

import com.ravn.timewars.shared.AbstractAuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "user")
@Entity
@Builder
public class User extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_sequence", allocationSize = 1, initialValue = 1)
    private Long id;

    private String name;

    private String email;

    private String status;

    private Integer costRate;

    public User() {
    }

    public User(Long id, String name, String email, String status, Integer costRate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.costRate = costRate;
    }
}
