package br.unesp.parking.manager.api.web.dto.mapper;

import br.unesp.parking.manager.api.entity.CarInfoParkingSpot;
import br.unesp.parking.manager.api.web.dto.ParkingCreateDto;
import br.unesp.parking.manager.api.web.dto.ParkingResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerParkingSpotMapper {
    public static CarInfoParkingSpot toCustomerParkingSpot(ParkingCreateDto dto) {
        return new ModelMapper().map(dto, CarInfoParkingSpot.class);
    }

    public static ParkingResponseDto toDto(CarInfoParkingSpot carInfoParkingSpot) {
        return new ModelMapper().map(carInfoParkingSpot, ParkingResponseDto.class);
    }
}
