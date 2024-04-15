package br.unesp.parking.manager.api.web.dto.mapper;

import br.unesp.parking.manager.api.entity.ParkingSpot;
import br.unesp.parking.manager.api.web.dto.ParkingSpotCreateDto;
import br.unesp.parking.manager.api.web.dto.ParkingSpotResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpotMapper {
    public static ParkingSpot toParkingSpot(ParkingSpotCreateDto dto) {
        return new ModelMapper().map(dto, ParkingSpot.class);
    }

    public static ParkingSpotResponseDto toDto(ParkingSpot parkingSpot) {
            return new ModelMapper().map(parkingSpot, ParkingSpotResponseDto.class);
    }
}
