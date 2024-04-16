package br.unesp.parking.manager.api.service;

import br.unesp.parking.manager.api.entity.CarInfo;
import br.unesp.parking.manager.api.entity.Customer;
import br.unesp.parking.manager.api.entity.User;
import br.unesp.parking.manager.api.exception.CpfUniqueViolationException;
import br.unesp.parking.manager.api.exception.EntityNotFoundException;
import br.unesp.parking.manager.api.repository.CustomerRepository;
import br.unesp.parking.manager.api.repository.projection.CustomerProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepo;

    @Transactional
    public Customer save(Customer customer) {
        try {
            customer.setBalance(BigDecimal.ZERO);
            return customerRepo.save(customer);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(String.format("CPF '%s' não pode ser cadastrado, já existe no sistema.", customer.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public Customer findById(Long id) {
        return customerRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente id=%s não encontrado no sistema", id))
        );
    }

    @Transactional(readOnly = true)
    public Customer findUserByIdAuthenticated(Long id) {
        return customerRepo.findByUserId(id);
    }

    @Transactional(readOnly = true)
    public Page<CustomerProjection> findAll(Pageable pageable) {
        return customerRepo.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Customer findByCpf(String cpf) {
        return customerRepo.findByCpf(cpf).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente com CPF '%s' não encontrado", cpf))
        );
    }

}
