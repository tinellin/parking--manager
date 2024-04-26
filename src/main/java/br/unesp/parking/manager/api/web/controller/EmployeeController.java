package br.unesp.parking.manager.api.web.controller;

import br.unesp.parking.manager.api.entity.Employee;
import br.unesp.parking.manager.api.repository.projection.EmployeeProjection;
import br.unesp.parking.manager.api.service.EmployeeService;
import br.unesp.parking.manager.api.web.dto.CreateEmployeeDto;
import br.unesp.parking.manager.api.web.dto.EmployeeResponseDto;
import br.unesp.parking.manager.api.web.dto.PageableDto;
import br.unesp.parking.manager.api.web.dto.mapper.EmployeeMapper;
import br.unesp.parking.manager.api.web.dto.mapper.PageableMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public PageableDto getAll(Pageable pageable) {
        Page<EmployeeProjection> employees = employeeService.findAll(pageable);
        return PageableMapper.toDto(employees);
    }
}
