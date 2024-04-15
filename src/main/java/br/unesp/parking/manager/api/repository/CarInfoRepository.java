package br.unesp.parking.manager.api.repository;

import br.unesp.parking.manager.api.entity.CarInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarInfoRepository extends JpaRepository<CarInfo, Long> {
}
