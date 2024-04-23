package br.unesp.parking.manager.api.web.controller;

import br.unesp.parking.manager.api.entity.CarInfo;
import br.unesp.parking.manager.api.entity.CustomerParkingSpot;
import br.unesp.parking.manager.api.jwt.JwtUserDetails;
import br.unesp.parking.manager.api.repository.projection.CustomerParkingSpotProjection;
import br.unesp.parking.manager.api.service.CustomerParkingSpotService;
import br.unesp.parking.manager.api.service.ParkingService;
import br.unesp.parking.manager.api.web.dto.CreateCarInfoDto;
import br.unesp.parking.manager.api.web.dto.PageableDto;
import br.unesp.parking.manager.api.web.dto.ParkingCreateDto;
import br.unesp.parking.manager.api.web.dto.ParkingResponseDto;
import br.unesp.parking.manager.api.web.dto.mapper.CarInfoMapper;
import br.unesp.parking.manager.api.web.dto.mapper.CustomerParkingSpotMapper;
import br.unesp.parking.manager.api.web.dto.mapper.PageableMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/parking")
@RequiredArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;
    private final CustomerParkingSpotService customerParkingSpotService;

    @PostMapping("/check-in")
    public ResponseEntity<ParkingResponseDto> checkIn(@RequestBody @Valid CreateCarInfoDto dto) {
        CarInfo carInfo = CarInfoMapper.toCarInfo(dto);
        CustomerParkingSpot customerParkingSpot = parkingService.checkIn(carInfo);
        ParkingResponseDto responseDto = CustomerParkingSpotMapper.toDto(customerParkingSpot);

        /* Adicionar location no header com o path de requisição para cliente-vaga criado */
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{receipt}")
                .buildAndExpand(responseDto.getReceipt())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);
    }

    @GetMapping("/check-in/{receipt}")
    public ParkingResponseDto getByReceipt(@PathVariable String receipt) {
        CustomerParkingSpot customerParkingSpot = customerParkingSpotService.findByReceipt(receipt);
        return CustomerParkingSpotMapper.toDto(customerParkingSpot);
    }

    @PutMapping("/pay/{receipt}")
    public ParkingResponseDto payReceipt(@PathVariable String receipt) {
        CustomerParkingSpot customerParkingSpot = parkingService.payReceipt(receipt);

        return CustomerParkingSpotMapper.toDto(customerParkingSpot);
    }

    @PutMapping("/check-out")
    public ParkingResponseDto checkout(@RequestBody @Valid CreateCarInfoDto dto) {
        CarInfo carInfo = CarInfoMapper.toCarInfo(dto);
        CustomerParkingSpot customerParkingSpot = parkingService.checkout(carInfo);

        return CustomerParkingSpotMapper.toDto(customerParkingSpot);
    }
}
