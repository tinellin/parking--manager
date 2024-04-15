package br.unesp.parking.manager.api.repository.projection;

import java.time.LocalTime;

public interface EmployeeProjection {
    Long getId();
    String getRole();
    LocalTime entryTime();
    LocalTime departureTime();
}
