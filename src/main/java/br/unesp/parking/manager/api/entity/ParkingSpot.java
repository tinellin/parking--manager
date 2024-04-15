package br.unesp.parking.manager.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "parking_spots")
@EntityListeners(AuditingEntityListener.class)
@Data
public class ParkingSpot implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 4)
    private String code;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusParkingSpot status;

    public enum StatusParkingSpot {
        FREE, OCCUPIED
    }

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeParkingSpot type;

    public enum TypeParkingSpot {
        REGULAR,
        PCD,
        SENIOR
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot parkingSpot = (ParkingSpot) o;
        return Objects.equals(id, parkingSpot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

