package br.unesp.parking.manager.api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Data @Builder
public class ParkingCreateDto {

    @NotBlank
    @Size(min = 7, max = 7)
    @Pattern(regexp = "[A-Z]{3}[0-9][A-Z][0-9]{2}", message = "A placa do veículo deve seguir o padrão Mercosul: XXX0X0XX")
    private String licensePlate;

    @NotBlank
    private String carBrand;

    @NotBlank
    private String carModel;

    @NotBlank
    private String carColor;
}