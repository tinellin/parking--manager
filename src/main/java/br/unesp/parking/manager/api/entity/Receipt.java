package br.unesp.parking.manager.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "receipt")
@Data
public class Receipt {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String receipt;

    @Column(name = "value", columnDefinition = "decimal(7,2)")
    private BigDecimal value;

    @Column(name = "discount", columnDefinition = "decimal(7,2)")
    private BigDecimal discount;

    @OneToOne(mappedBy = "receipt")
    private ClientParkingSpot clientParkingSpot;

}
