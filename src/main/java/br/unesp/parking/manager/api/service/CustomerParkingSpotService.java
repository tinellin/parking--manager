package br.unesp.parking.manager.api.service;

import br.unesp.parking.manager.api.entity.CustomerParkingSpot;
import br.unesp.parking.manager.api.exception.EntityNotFoundException;
import br.unesp.parking.manager.api.repository.CustomerParkingSpotRepository;
import br.unesp.parking.manager.api.repository.projection.CustomerParkingSpotProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerParkingSpotService {

    private final CustomerParkingSpotRepository customerParkingSpotRepo;

    @Transactional
    public CustomerParkingSpot save(CustomerParkingSpot customerParkingSpot) {
        return customerParkingSpotRepo.save(customerParkingSpot);
    }

    @Transactional(readOnly = true)
    public CustomerParkingSpot findByReceipt(String receipt) {
        return customerParkingSpotRepo.findByReceiptAndEndDateIsNull(receipt).orElseThrow(
                () -> new EntityNotFoundException(
                        String.format("Recibo %s não encontrado no sistema ou check-out já realizado.", receipt)
                )
        );
    }

    @Transactional(readOnly = true)
    public CustomerParkingSpot findByCarInfo(String licensePlate) {
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
