package br.unesp.parking.manager.api.repository.projection;

import br.unesp.parking.manager.api.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CustomerProjection {
    Long getId();
    String getName();
    // LocalDate getBirthday();
    String getCpf();
    String getOccupation();
    String getUsername();
}
