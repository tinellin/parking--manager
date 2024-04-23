package br.unesp.parking.manager.api.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
@DiscriminatorValue("customer")
@Data
public class Customer extends Person {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "balance", columnDefinition = "decimal(7,2)", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "occupation", nullable = false, length = 100)
    private String occupation;

    @OneToMany(mappedBy = "customer")
    private List<CarInfo> car;


    public void setBalance(BigDecimal balance) {
        this.balance = this.balance.add(balance);
    }
}
