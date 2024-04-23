package br.unesp.parking.manager.api.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParkingResponseDto {
    private String licensePlate;
    private String carBrand;
    private String carModel;
    private String carColor;
    private String receipt;
    @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss")
    private LocalDateTime entryDate;
    @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss")
    private LocalDateTime endDate;
    private String carSpaceCode;
    private BigDecimal value;
    private BigDecimal discount;
}
