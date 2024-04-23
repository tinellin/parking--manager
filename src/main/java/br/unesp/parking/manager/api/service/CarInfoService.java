package br.unesp.parking.manager.api.service;

import br.unesp.parking.manager.api.entity.CarInfo;
import br.unesp.parking.manager.api.entity.Customer;
import br.unesp.parking.manager.api.exception.EntityNotFoundException;
import br.unesp.parking.manager.api.exception.LicensePlateUniqueViolationException;
import br.unesp.parking.manager.api.repository.CarInfoRepository;
import br.unesp.parking.manager.api.repository.CustomerRepository;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@ToString
public class CarInfoService {
    @Autowired
    private CarInfoRepository carInfoRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Transactional
    public CarInfo createByCustomer(Customer customer, CarInfo carInfo) {
        try {
            Optional<CarInfo> hasCarInfo = carInfoRepo.findByLicensePlate(carInfo.getLicensePlate());

            if (hasCarInfo.isPresent()) {
                hasCarInfo.get().setCustomer(customer);
                return carInfoRepo.save(hasCarInfo.get());
            }

            carInfo.setCustomer(customer);
            return carInfoRepo.save(carInfo);
        } catch (DataIntegrityViolationException ex) {
            throw new LicensePlateUniqueViolationException(String.format("Placa  de veículo '%s' não pode ser cadastrado, pois já existe no sistema.", carInfo.getLicensePlate()));
        }
    }

    @Transactional
    public CarInfo createByArrivalInParking(CarInfo carInfo) {
        Optional<CarInfo> hasCar = carInfoRepo.findByLicensePlate(carInfo.getLicensePlate());

        return hasCar.orElseGet(() -> carInfoRepo.save(carInfo));
    }

    @Transactional
    public void delete(String licensePlate) {
        CarInfo carInfo = carInfoRepo.findByLicensePlate(licensePlate.toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException(String.format("O carro com placa=%s não existe no sistema.", licensePlate)));
        carInfoRepo.delete(carInfo);
    }
}
