package br.unesp.parking.manager.api.web.dto;

import br.unesp.parking.manager.api.entity.User;
import lombok.Data;

import java.time.LocalTime;

@Data
public class EmployeeResponseDto {
    private Long id;
    private String name;
    private String role;
    private String entryTime;
    private String departureTime;
}
