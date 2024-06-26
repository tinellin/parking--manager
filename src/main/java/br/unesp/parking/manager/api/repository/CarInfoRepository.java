package br.unesp.parking.manager.api.repository;

import br.unesp.parking.manager.api.entity.CarInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarInfoRepository extends JpaRepository<CarInfo, Long> {
    Optional<CarInfo> findByLicensePlate(String licensePlate);

    List<CarInfo> findByCustomerId(Long id);
}
