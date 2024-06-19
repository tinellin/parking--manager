package br.unesp.parking.manager.api.service;

import br.unesp.parking.manager.api.entity.CarInfo;
import br.unesp.parking.manager.api.entity.Customer;
import br.unesp.parking.manager.api.entity.CarInfoParkingSpot;
import br.unesp.parking.manager.api.entity.ParkingSpot;
import br.unesp.parking.manager.api.exception.CustomerPaymentException;
import br.unesp.parking.manager.api.utils.ParkingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ParkingService {
    private final CarInfoParkingSpotService carInfoParkingSpotService;
    private final ParkingSpotService parkingSpotService;
    private final CarInfoService carInfoService;

    @Transactional
    public CarInfoParkingSpot checkIn(CarInfo carInfo) {
        CarInfoParkingSpot carInfoParkingSpot = new CarInfoParkingSpot();

        /* Criar registro de carro, se não passou pelo estacionamento ainda */
        CarInfo createdCar = carInfoService.createByArrivalInParking(carInfo);
        carInfoParkingSpot.setCarInfo(createdCar);

        /* Busca por uma vaga livre por um querie method que procura a primeira vaga livre */
        ParkingSpot parkingSpot = parkingSpotService.getFreeParkingSpot();
        parkingSpot.setStatus(ParkingSpot.StatusParkingSpot.OCCUPIED);

        /* Insere o objeto vaga, a data de entrada e o recibo (usando o gerador de recibo personalizado que criamos) */
        carInfoParkingSpot.setParkingSpot(parkingSpot);
        carInfoParkingSpot.setEntryDate(LocalDateTime.now());
        carInfoParkingSpot.setReceipt(ParkingUtils.generateReceipt());

        System.out.println("Recibo: " + carInfoParkingSpot.getReceipt());

        return carInfoParkingSpotService.save(carInfoParkingSpot);
    }

    @Transactional
    public CarInfoParkingSpot checkout(CarInfo carInfo) {
        CarInfoParkingSpot carInfoParkingSpot = carInfoParkingSpotService.findByCarInfo(carInfo.getLicensePlate());
        Customer customer = carInfoParkingSpot.getCarInfo().getCustomer();

        if (customer == null && carInfoParkingSpot.getValue().equals(BigDecimal.ZERO)) {
            throw new CustomerPaymentException(
                    String.format("Cancela não liberada, pois a placa %s não realizou o pagamento do recibo.", carInfo.getLicensePlate())
            );
        }

        if (customer != null) {
            if (customer.getBalance().compareTo(BigDecimal.ZERO) <= 0) {
                throw new CustomerPaymentException(
                        String.format("O cliente %s não possui saldo suficiente para realizar o pagamento.", customer.getCpf())
                );
            }

            injectCheckoutData(carInfoParkingSpot);

            /* Calcular e inserir desconto */
            long totalOfTimes = carInfoParkingSpotService.getTotalTimesFullParking(carInfoParkingSpot.getCarInfo().getLicensePlate());
            BigDecimal discount = ParkingUtils.calculateDiscount(carInfoParkingSpot.getValue(), totalOfTimes);
            carInfoParkingSpot.setDiscount(discount);
            customer.subtractBalance(carInfoParkingSpot.getValue().subtract(discount));

            carInfoParkingSpotService.save(carInfoParkingSpot);
        }

        return carInfoParkingSpot;
    }

    @Transactional
    public CarInfoParkingSpot payReceipt(String receipt) {
        CarInfoParkingSpot carInfoParkingSpot = carInfoParkingSpotService.findByReceipt(receipt);
        injectCheckoutData(carInfoParkingSpot);

        return carInfoParkingSpotService.save(carInfoParkingSpot);
    }

    private void injectCheckoutData(CarInfoParkingSpot carInfoParkingSpot) {
        LocalDateTime endDate = LocalDateTime.now();

        /* Calcular custo total do estacionamento */
        BigDecimal value = ParkingUtils.calculateCost(carInfoParkingSpot.getEntryDate(), endDate);

        carInfoParkingSpot.setEndDate(endDate);
        carInfoParkingSpot.setValue(value);
        carInfoParkingSpot.getParkingSpot().setStatus(ParkingSpot.StatusParkingSpot.FREE);
    }
}
