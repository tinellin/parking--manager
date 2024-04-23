package br.unesp.parking.manager.api.service;

import br.unesp.parking.manager.api.entity.ParkingSpot;
import br.unesp.parking.manager.api.exception.CodeUniqueViolationException;
import br.unesp.parking.manager.api.exception.EntityNotFoundException;
import br.unesp.parking.manager.api.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepo;

    @Transactional
    public void save(ParkingSpot parkingSpot) {
        try {
            parkingSpot.setStatus(ParkingSpot.StatusParkingSpot.FREE);
            parkingSpotRepo.save(parkingSpot);
        } catch (DataIntegrityViolationException ex) {
            throw new CodeUniqueViolationException(
                    String.format("Vaga com código '%s' já cadastrada", parkingSpot.getCode())
            );
        }
    }

    @Transactional(readOnly = true)
    public ParkingSpot findByCode(String code) {
        return parkingSpotRepo.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException(String.format("Vaga com código %s não foi encontrada.", code))
        );
    }

    @Transactional(readOnly = true)
    public List<ParkingSpot> findAll() {
        return parkingSpotRepo.findAll();
    }

    @Transactional
    public ParkingSpot getFreeParkingSpot() {
        return parkingSpotRepo.findFirstByStatus(ParkingSpot.StatusParkingSpot.FREE)
                .orElseThrow(() -> new EntityNotFoundException("Nenhuma vaga livre foi encontrada."));
    }
}
