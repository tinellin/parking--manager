package br.unesp.parking.manager.api.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class UserPasswordDto {
    @NotBlank
    @Size(min = 6, max = 6)
    private String password;

    @NotBlank
    @Size(min = 6, max = 6)
    private String newPassword;

    @NotBlank
    @Size(min = 6, max = 6)
    private String confirmNewPassword;
}