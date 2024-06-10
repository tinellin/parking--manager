package br.unesp.parking.manager.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "employees")
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("employee")
@Data
public class Employee extends Person {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "registration", unique = true)
    private String workUUID;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "entry_time", nullable = false)
    private String entryTime;

    @Column(name = "departure_time", nullable = false)
    private String departureTime;

}
