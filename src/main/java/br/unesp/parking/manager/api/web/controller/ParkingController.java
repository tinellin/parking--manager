package br.unesp.parking.manager.api.web.controller;

import br.unesp.parking.manager.api.entity.CarInfo;
import br.unesp.parking.manager.api.entity.CarInfoParkingSpot;
import br.unesp.parking.manager.api.repository.projection.CarInfoParkingSpotProjection;
import br.unesp.parking.manager.api.service.CarInfoParkingSpotService;
import br.unesp.parking.manager.api.service.ParkingService;
import br.unesp.parking.manager.api.web.dto.CreateCarInfoDto;
import br.unesp.parking.manager.api.web.dto.PageableDto;
import br.unesp.parking.manager.api.web.dto.ParkingResponseDto;
import br.unesp.parking.manager.api.web.dto.mapper.CarInfoMapper;
import br.unesp.parking.manager.api.web.dto.mapper.CustomerParkingSpotMapper;
import br.unesp.parking.manager.api.web.dto.mapper.PageableMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;
    private final CarInfoParkingSpotService carInfoParkingSpotService;

    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponseDto> checkIn(@RequestBody @Valid CreateCarInfoDto dto) {
        CarInfo carInfo = CarInfoMapper.toCarInfo(dto);
        CarInfoParkingSpot carInfoParkingSpot = parkingService.checkIn(carInfo);
        ParkingResponseDto responseDto = CustomerParkingSpotMapper.toDto(carInfoParkingSpot);

        /* Adicionar location no header com o path de requisição para cliente-vaga criado */
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{receipt}")
                .buildAndExpand(responseDto.getReceipt())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping("/check-in/{receipt}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ParkingResponseDto getByReceipt(@PathVariable String receipt) {
        CarInfoParkingSpot carInfoParkingSpot = carInfoParkingSpotService.findByReceipt(receipt);
        return CustomerParkingSpotMapper.toDto(carInfoParkingSpot);
    }

    @PutMapping("/pay/{receipt}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ParkingResponseDto payReceipt(@PathVariable String receipt) {
        CarInfoParkingSpot carInfoParkingSpot = parkingService.payReceipt(receipt);

        return CustomerParkingSpotMapper.toDto(carInfoParkingSpot);
    }

    @PutMapping("/check-out")
    @PreAuthorize("hasRole('ADMIN')")
    public ParkingResponseDto checkout(@RequestBody @Valid CreateCarInfoDto dto) {
        CarInfo carInfo = CarInfoMapper.toCarInfo(dto);
        CarInfoParkingSpot carInfoParkingSpot = parkingService.checkout(carInfo);

        return CustomerParkingSpotMapper.toDto(carInfoParkingSpot);
    }

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN')")
    public PageableDto getAllParking(Pageable pageable) {
        Page<CarInfoParkingSpotProjection> parkings = carInfoParkingSpotService.findAllCarInfoParkingSpots(pageable);
        return PageableMapper.toDto(parkings);
    }
}
