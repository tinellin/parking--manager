package br.unesp.parking.manager.api.web.controller;

import br.unesp.parking.manager.api.entity.ParkingSpot;
import br.unesp.parking.manager.api.service.ParkingSpotService;
import br.unesp.parking.manager.api.web.dto.ParkingSpotCreateDto;
import br.unesp.parking.manager.api.web.dto.ParkingSpotResponseDto;
import br.unesp.parking.manager.api.web.dto.mapper.ParkingSpotMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/parking-spots")
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid ParkingSpotCreateDto dto) {
        ParkingSpot parkingSpot = ParkingSpotMapper.toParkingSpot(dto);
        parkingSpotService.save(parkingSpot);

        /* Adicionar location no header com o path de requisição para a vaga criada */
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(parkingSpot.getCode())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<ParkingSpot> getAll() {
        return parkingSpotService.findAll();
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ParkingSpotResponseDto getByCode(@PathVariable String code) {
        return ParkingSpotMapper.toDto(parkingSpotService.findByCode(code));
    }
}
