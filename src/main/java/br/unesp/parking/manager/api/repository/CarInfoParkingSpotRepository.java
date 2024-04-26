package br.unesp.parking.manager.api.repository;

import br.unesp.parking.manager.api.entity.CarInfoParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarInfoParkingSpotRepository extends JpaRepository<CarInfoParkingSpot, Long> {
    Optional<CarInfoParkingSpot> findByReceiptAndEndDateIsNull(String receipt);

    Optional<CarInfoParkingSpot> findByCarInfoLicensePlateAndEndDateIsNull(String licensePlate);

    long countByCarInfoLicensePlateAndEndDateIsNotNull(String licensePlate);
}
