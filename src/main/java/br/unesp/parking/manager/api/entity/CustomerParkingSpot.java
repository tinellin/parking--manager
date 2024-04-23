package br.unesp.parking.manager.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "customer_has_parking_spot")
@EntityListeners(AuditingEntityListener.class)
@Data
public class CustomerParkingSpot {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entry_date", nullable = false)
    private LocalDateTime entryDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "receipt_code", nullable = false, length = 15)
    private String receipt;

    @Column(name = "value", columnDefinition = "decimal(7,2)")
    private BigDecimal value = BigDecimal.ZERO;

    @Column(name = "discount", columnDefinition = "decimal(7,2)")
    private BigDecimal discount = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "car_info_id", nullable = false)
    private CarInfo carInfo;

    @ManyToOne
    @JoinColumn(name = "id_parking_spot", nullable = false)
    private ParkingSpot parkingSpot;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerParkingSpot that = (CustomerParkingSpot) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
