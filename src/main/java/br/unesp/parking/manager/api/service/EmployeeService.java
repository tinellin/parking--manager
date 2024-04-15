package br.unesp.parking.manager.api.service;

import br.unesp.parking.manager.api.entity.Employee;
import br.unesp.parking.manager.api.exception.CpfUniqueViolationException;
import br.unesp.parking.manager.api.exception.EntityNotFoundException;
import br.unesp.parking.manager.api.repository.EmployeeRepository;
import br.unesp.parking.manager.api.repository.projection.EmployeeProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepo;

    @Transactional
    public Employee save(Employee employee) {
        try {
            employee.setWorkUUID(String.valueOf(UUID.randomUUID()));
            return employeeRepo.save(employee);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(String.format("CPF '%s' não pode ser cadastrado, já existe no sistema.", employee.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public Page<EmployeeProjection> findAll(Pageable pageable) {
        return employeeRepo.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Employee getById(Long id) {
        return employeeRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Empregado id=%s não encontrado no sistema", id))
        );
    }
}
