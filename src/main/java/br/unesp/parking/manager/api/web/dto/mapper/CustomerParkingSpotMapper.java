package br.unesp.parking.manager.api.web.dto.mapper;

import br.unesp.parking.manager.api.entity.CustomerParkingSpot;
import br.unesp.parking.manager.api.web.dto.ParkingCreateDto;
import br.unesp.parking.manager.api.web.dto.ParkingResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerParkingSpotMapper {
    public static CustomerParkingSpot toCustomerParkingSpot(ParkingCreateDto dto) {
        return new ModelMapper().map(dto, CustomerParkingSpot.class);
    }

    public static ParkingResponseDto toDto(CustomerParkingSpot customerParkingSpot) {
        return new ModelMapper().map(customerParkingSpot, ParkingResponseDto.class);
    }
}
