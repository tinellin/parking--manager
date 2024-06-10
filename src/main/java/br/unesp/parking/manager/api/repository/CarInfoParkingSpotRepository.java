package br.unesp.parking.manager.api.repository;

import br.unesp.parking.manager.api.entity.CarInfoParkingSpot;
import br.unesp.parking.manager.api.repository.projection.CarInfoParkingSpotProjection;
import br.unesp.parking.manager.api.repository.projection.CustomerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarInfoParkingSpotRepository extends JpaRepository<CarInfoParkingSpot, Long> {
    Optional<CarInfoParkingSpot> findByReceiptAndEndDateIsNull(String receipt);

    Optional<CarInfoParkingSpot> findByCarInfoLicensePlateAndEndDateIsNull(String licensePlate);

    long countByCarInfoLicensePlateAndEndDateIsNotNull(String licensePlate);

    @Query("SELECT " +
            "ci.licensePlate AS carInfoLicensePlate, " +
            "ci.carBrand AS carInfoCarBrand, " +
            "ci.carModel AS carInfoCarModel, " +
            "ci.carColor AS carInfoCarColor, " +
            "c.cpf AS customerCpf, " +
            "cp.receipt AS receipt, " +
            "cp.entryDate AS entryDate, " +
            "cp.endDate AS endDate, " +
            "ps.code AS carSpaceCode, " +
            "cp.value AS value, " +
            "cp.discount AS discount " +
            "FROM CarInfoParkingSpot cp " +
            "JOIN cp.carInfo ci " +
            "JOIN ci.customer c " +
            "JOIN cp.parkingSpot ps")
    Page<CarInfoParkingSpotProjection> findAllPageable(Pageable pageable);
}
