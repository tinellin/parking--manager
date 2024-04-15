package br.unesp.parking.manager.api.web.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class EmployeeResponseDto {
    private Long id;
    private String name;
    private String role;
    private LocalTime entryTime;
    private LocalTime departureTime;
}
