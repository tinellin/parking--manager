package br.unesp.parking.manager.api.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateCustomerDto {
    @NotNull
    @Size(min = 5, max = 100)
    private String name;

    @Size(min = 11, max = 11)
    @CPF
    private String cpf;

    @NotBlank
    private String occupation;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
}
