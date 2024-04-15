package br.unesp.parking.manager.api.repository;

import br.unesp.parking.manager.api.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    Optional<ParkingSpot> findByCode(String code);

    Optional<ParkingSpot> findFirstByStatus(ParkingSpot.StatusParkingSpot statusParkingSpot);
}
