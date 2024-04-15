package br.unesp.parking.manager.api.web.dto;

import lombok.*;

@Data
public class ParkingSpotResponseDto {

    private Long id;
    private String code;
    private String type;
    private String status;
}
