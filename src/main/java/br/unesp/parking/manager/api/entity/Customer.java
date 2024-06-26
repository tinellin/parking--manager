package br.unesp.parking.manager.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customers")
@DiscriminatorValue("customer")
@Data
public class Customer extends Person {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "balance", columnDefinition = "decimal(7,2)", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "occupation", nullable = false, length = 100)
    private String occupation;

    public void setBalance(BigDecimal balance) {
        this.balance = this.balance.add(balance);
    }

    public void subtractBalance(BigDecimal balance) {
        this.balance = this.balance.subtract(balance);
    }
}
