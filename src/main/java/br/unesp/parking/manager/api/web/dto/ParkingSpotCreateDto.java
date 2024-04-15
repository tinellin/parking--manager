package br.unesp.parking.manager.api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class ParkingSpotCreateDto {

    @NotBlank
    @Size(min = 4, max = 4)
    private String code;

    @NotBlank
    @Pattern(regexp = "REGULAR|PCD|SENIOR")
    private String type;
}
