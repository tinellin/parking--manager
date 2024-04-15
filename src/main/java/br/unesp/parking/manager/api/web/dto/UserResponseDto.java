package br.unesp.parking.manager.api.web.dto;

import lombok.*;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String role;
}