package br.unesp.parking.manager.api.repository;

import br.unesp.parking.manager.api.entity.CarInfo;
import br.unesp.parking.manager.api.entity.CustomerParkingSpot;
import br.unesp.parking.manager.api.repository.projection.CustomerParkingSpotProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerParkingSpotRepository extends JpaRepository<CustomerParkingSpot, Long> {
    Optional<CustomerParkingSpot> findByReceiptAndEndDateIsNull(String receipt);

    Optional<CustomerParkingSpot> findByCarInfoLicensePlateAndEndDateIsNull(String licensePlate);

    long countByCarInfoLicensePlateAndEndDateIsNotNull(String licensePlate);
}
