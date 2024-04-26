package br.unesp.parking.manager.api.service;

import br.unesp.parking.manager.api.entity.CarInfoParkingSpot;
import br.unesp.parking.manager.api.exception.EntityNotFoundException;
import br.unesp.parking.manager.api.repository.CarInfoParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CarInfoParkingSpotService {

    private final CarInfoParkingSpotRepository customerParkingSpotRepo;

    @Transactional
    public CarInfoParkingSpot save(CarInfoParkingSpot carInfoParkingSpot) {
        return customerParkingSpotRepo.save(carInfoParkingSpot);
    }

    @Transactional(readOnly = true)
    public CarInfoParkingSpot findByReceipt(String receipt) {
        return customerParkingSpotRepo.findByReceiptAndEndDateIsNull(receipt).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Recibo %s não encontrado no sistema ou check-out já realizado.", receipt)
                )
        );
    }

    @Transactional(readOnly = true)
    public CarInfoParkingSpot findByCarInfo(String licensePlate) {
        return customerParkingSpotRepo.findByCarInfoLicensePlateAndEndDateIsNull(licensePlate).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Placa %s não encontrado no sistema ou check-out já realizado.", licensePlate)
                )
        );
    }

    @Transactional(readOnly = true)
    public long getTotalTimesFullParking(String licensePlate) {
        return customerParkingSpotRepo.countByCarInfoLicensePlateAndEndDateIsNotNull(licensePlate);
    }
}
