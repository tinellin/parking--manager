package br.unesp.parking.manager.api.service;

import br.unesp.parking.manager.api.entity.CarInfo;
import br.unesp.parking.manager.api.entity.Customer;
import br.unesp.parking.manager.api.entity.User;
import br.unesp.parking.manager.api.exception.EntityNotFoundException;
import br.unesp.parking.manager.api.exception.LicensePlateUniqueViolationException;
import br.unesp.parking.manager.api.repository.CarInfoRepository;
import br.unesp.parking.manager.api.repository.CustomerRepository;
import br.unesp.parking.manager.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@ToString
public class CarInfoService {
    @Autowired
    private CarInfoRepository carInfoRepo;

    @Autowired
    private CustomerRepository customerRepo;

    public CarInfo create(Customer customer, CarInfo carInfo) {
        try {
            carInfo.setCustomer(customer);
            return carInfoRepo.save(carInfo);
        } catch (DataIntegrityViolationException ex) {
            throw new LicensePlateUniqueViolationException(String.format("Placa  de veículo '%s' não pode ser cadastrado, pois já existe no sistema.", carInfo.getLicensePlate()));
        }
    }

    public void delete(String licensePlate) {
        CarInfo carInfo = carInfoRepo.findByLicensePlate(licensePlate.toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException(String.format("O carro com placa=%s não existe no sistema.", licensePlate)));
        carInfoRepo.delete(carInfo);
    }
}
