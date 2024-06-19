package br.unesp.parking.manager.api.repository;

import br.unesp.parking.manager.api.entity.ParkingSpot;
import br.unesp.parking.manager.api.repository.projection.ParkingSpotProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    Optional<ParkingSpot> findByCode(String code);

    Optional<ParkingSpot> findFirstByStatus(ParkingSpot.StatusParkingSpot statusParkingSpot);

    @Query("SELECT c.id AS id, c.code AS code, c.status AS status, c.type AS type FROM ParkingSpot c")
    Page<ParkingSpotProjection> findAllPageable(Pageable pageable);
}
