package br.unesp.parking.manager.api.web.dto.mapper;

import br.unesp.parking.manager.api.entity.CarInfo;
import br.unesp.parking.manager.api.web.dto.CarInfoResponseDto;
import br.unesp.parking.manager.api.web.dto.CreateCarInfoDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarInfoMapper {

    public static CarInfo toCarInfo(CreateCarInfoDto dto) {
        return new ModelMapper().map(dto, CarInfo.class);
    }

    public static CarInfoResponseDto toDto(CarInfo carInfo) {
        return new ModelMapper().map(carInfo, CarInfoResponseDto.class);
    }
}
