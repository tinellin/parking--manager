package br.unesp.parking.manager.api.web.dto;

import lombok.*;

@Data
public class CustomerResponseDto {
    private Long id;
    private String name;
    private String cpf;
}
