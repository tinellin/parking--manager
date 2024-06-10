package br.unesp.parking.manager.api.service;

import br.unesp.parking.manager.api.entity.CarInfoParkingSpot;
import br.unesp.parking.manager.api.exception.EntityNotFoundException;
import br.unesp.parking.manager.api.repository.CarInfoParkingSpotRepository;
import br.unesp.parking.manager.api.repository.projection.CarInfoParkingSpotProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarInfoParkingSpotService {

    private final CarInfoParkingSpotRepository carInfoParkingSpotRepo;

    @Transactional
    public CarInfoParkingSpot save(CarInfoParkingSpot carInfoParkingSpot) {
        return carInfoParkingSpotRepo.save(carInfoParkingSpot);
    }

    @Transactional(readOnly = true)
    public CarInfoParkingSpot findByReceipt(String receipt) {
        return carInfoParkingSpotRepo.findByReceiptAndEndDateIsNull(receipt).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Recibo %s não encontrado no sistema ou check-out já realizado.", receipt)
                )
        );
    }

    @Transactional(readOnly = true)
    public CarInfoParkingSpot findByCarInfo(String licensePlate) {
        return carInfoParkingSpotRepo.findByCarInfoLicensePlateAndEndDateIsNull(licensePlate).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Placa %s não encontrado no sistema ou check-out já realizado.", licensePlate)
                )
        );
    }

    @Transactional(readOnly = true)
    public long getTotalTimesFullParking(String licensePlate) {
        return carInfoParkingSpotRepo.countByCarInfoLicensePlateAndEndDateIsNotNull(licensePlate);
    }

    @Transactional(readOnly = true)
    public Page<CarInfoParkingSpotProjection> findAllCarInfoParkingSpots(Pageable pageable) {
        return carInfoParkingSpotRepo.findAllPageable(pageable);
    }
}
