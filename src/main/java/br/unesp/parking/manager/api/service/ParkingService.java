package br.unesp.parking.manager.api.service;

import br.unesp.parking.manager.api.entity.CarInfo;
import br.unesp.parking.manager.api.entity.Customer;
import br.unesp.parking.manager.api.entity.CustomerParkingSpot;
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
    private final CustomerParkingSpotService customerParkingSpotService;
    private final CustomerService customerService;
    private final ParkingSpotService parkingSpotService;
    private final CarInfoService carInfoService;

    @Transactional
    public CustomerParkingSpot checkIn(CarInfo carInfo) {
        CustomerParkingSpot customerParkingSpot = new CustomerParkingSpot();

        /* Criar registro de carro, se não passou pelo estacionamento ainda */
        CarInfo createdCar = carInfoService.createByArrivalInParking(carInfo);
        customerParkingSpot.setCarInfo(createdCar);

        /* Busca por uma vaga livre por um querie method que procura a primeira vaga livre */
        ParkingSpot parkingSpot = parkingSpotService.getFreeParkingSpot();
        parkingSpot.setStatus(ParkingSpot.StatusParkingSpot.OCCUPIED);

        /* Insere o objeto vaga, a data de entrada e o recibo (usando o gerador de recibo personalizado que criamos) */
        customerParkingSpot.setParkingSpot(parkingSpot);
        customerParkingSpot.setEntryDate(LocalDateTime.now());
        customerParkingSpot.setReceipt(ParkingUtils.generateReceipt());

        System.out.println("Recibo: " + customerParkingSpot.getReceipt());

        return customerParkingSpotService.save(customerParkingSpot);
    }

    @Transactional
    public CustomerParkingSpot checkout(CarInfo carInfo) {
        CustomerParkingSpot customerParkingSpot = customerParkingSpotService.findByCarInfo(carInfo.getLicensePlate());
        Customer customer = customerParkingSpot.getCarInfo().getCustomer();

        if (customer == null && customerParkingSpot.getValue().equals(BigDecimal.ZERO)) {
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

            injectCheckoutData(customerParkingSpot);

            /* Calcular e inserir desconto */
            long totalOfTimes = customerParkingSpotService.getTotalTimesFullParking(customerParkingSpot.getCarInfo().getLicensePlate());
            BigDecimal discount = ParkingUtils.calculateDiscount(customerParkingSpot.getValue(), totalOfTimes);
            customerParkingSpot.setDiscount(discount);

            return customerParkingSpotService.save(customerParkingSpot);
        }

        return null;
    }

    @Transactional
    public CustomerParkingSpot payReceipt(String receipt) {
        CustomerParkingSpot customerParkingSpot = customerParkingSpotService.findByReceipt(receipt);
        injectCheckoutData(customerParkingSpot);

        return customerParkingSpotService.save(customerParkingSpot);
    }

    private void injectCheckoutData(CustomerParkingSpot customerParkingSpot) {
        LocalDateTime endDate = LocalDateTime.now();

        /* Calcular custo total do estacionamento */
        BigDecimal value = ParkingUtils.calculateCost(customerParkingSpot.getEntryDate(), endDate);

        customerParkingSpot.setEndDate(endDate);
        customerParkingSpot.setValue(value);
        customerParkingSpot.getParkingSpot().setStatus(ParkingSpot.StatusParkingSpot.FREE);
    }
}
