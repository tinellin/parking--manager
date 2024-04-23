package br.unesp.parking.manager.api.web.dto;

import br.unesp.parking.manager.api.entity.CarInfo;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CustomerResponseDto {
    private Long id;
    private String name;
    private String cpf;
    private String occupation;
    private BigDecimal balance;
    private List<CarInfo> car;
}
