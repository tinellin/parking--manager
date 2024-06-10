package br.unesp.parking.manager.api.web.controller;

import br.unesp.parking.manager.api.entity.Employee;
import br.unesp.parking.manager.api.service.EmployeeService;
import br.unesp.parking.manager.api.web.dto.CreateEmployeeDto;
import br.unesp.parking.manager.api.web.dto.EmployeeResponseDto;
import br.unesp.parking.manager.api.web.dto.mapper.EmployeeMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeResponseDto create(@RequestBody @Valid CreateEmployeeDto dto) {
        Employee employee = employeeService.save(EmployeeMapper.toEmployee(dto));
        return EmployeeMapper.toDto(employee);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public EmployeeResponseDto getById(@PathVariable Long id) {
        return EmployeeMapper.toDto(employeeService.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<EmployeeResponseDto> getAll() {
        List<Employee> employees = employeeService.findAll();
        return EmployeeMapper.toDtoList(employees);
    }
}
