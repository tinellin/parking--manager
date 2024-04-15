package br.unesp.parking.manager.api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@ToString
public class UserLoginDto {
    @NotBlank
    @Email(message = "formato do e-mail está invalido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")
    private String username;

    @NotBlank
    @Size(min = 6, max = 6)
    private String password;
}
