package br.unesp.parking.manager.api.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CarInfoResponseDto {
    private String carBrand;
    private String carModel;
    private String carColor;
}
